package com.B1team.b01.service;

import com.B1team.b01.dto.LotDto;
import com.B1team.b01.dto.RorderDto;
import com.B1team.b01.dto.RorderFormDto;
import com.B1team.b01.entity.Finprod;
import com.B1team.b01.entity.Rorder;
import com.B1team.b01.entity.Worder;
import com.B1team.b01.repository.LotRepository;
import com.B1team.b01.repository.RorderRepository;
import com.B1team.b01.repository.WorderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RorderService {
    private final RorderRepository rorderRepository;
    private final WorderRepository worderRepository;
    private final MprocessService mprocessService;
    private final WplanService wplanService;
    private final WorderService worderService;
    private final WperformService wperformService;
    private final PinoutService pinoutService;
    private final FinprodService finprodService;
    private final StockService stockService;

    private final LotService lotService;

    private final EntityManager entityManager;
    private final LotRepository lotRepository;


    //수주 - 확정 시 이벤트
    public void rorderConfirmed(String rorderId) {
        Rorder rorder = updateConfirmed(rorderId);


//            1 제품 재고 업데이트 - 수경님
//            stockService.stockCheck();

//            2 원자재 재고 업뎃 - 세윤님
//            stockService.updateStockEa();

//            3 자동 발주 / 발주상세 자재 ,입출 정보 in - 수경님

//            4 생산 지시, 로트번호, 생산계획, 실적, 완제품 insert -다인님
                LocalDateTime materialReadyDate = rorder.getDate();
                String productId = rorder.getProductId();
                long orderCnt = rorder.getCnt();
                String orderId = rorder.getId();
                wplanService.createWplan(materialReadyDate, productId, orderCnt, orderId);  //작성계획 등록메소드

                List<LotDto> lotDtoList = worderService.doWorder(orderId, materialReadyDate, productId, orderCnt);    //작업지시 등록메소드

                stockService.deleteStockEa(productId, orderCnt);//출고
                pinoutService.createMTROut(orderId, productId);    //자재입출정보등록


                wperformService.insertWperform(orderId);    //작업실적 등록
                Finprod finprod = finprodService.insertFinprod(orderId);      //완제품 등록

                for(LotDto lotDto : lotDtoList) {
                    lotDto.setFinprodId(finprod.getId());
                    lotRepository.save(lotDto.toEntity());
                }











    }

    //수주 - 확정 시 : 수주일자 조정 / 시뮬레이션 돌리기 / 확정 변환
    public Rorder updateConfirmed(String rorderId) {
        Optional<Rorder> optional = rorderRepository.findById(rorderId);
        Rorder rorder = optional.get();

        //수주일이 현재 시간 보다 이전이면 지금 시간으로 수정
        if(rorder.getDate().isBefore(LocalDateTime.now()))
            rorder.setDate(LocalDateTime.now());

        //시뮬레이션 돌리기
        LocalDateTime deliveryDate = mprocessService.caluculateDeadline(rorder.getDate(), rorder.getProductId(), rorder.getCnt());
        rorder.setDeadline(deliveryDate);

        //미확정에서 확정으로 세팅
        rorder.setState("확정");
        return rorderRepository.save(rorder);
    }

    //수주 리스트
    public List<RorderDto> searchRorder(String start, String end, String orderId, String state, String customerName, String productName, String startLine, String endLine) {
        //날짜 관련 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDate = start == null || "".equals(start)? null : LocalDate.parse(start, formatter).atStartOfDay();
        LocalDateTime endDate = end == null || "".equals(end) ? null : LocalDate.parse(end, formatter).atTime(23, 59, 59);
        LocalDateTime startDeadline = startLine == null || "".equals(startLine) ? null : LocalDate.parse(startLine, formatter).atStartOfDay();
        LocalDateTime endDeadLine = endLine == null || "".equals(endLine) ? null : LocalDate.parse(endLine, formatter).atTime(23, 59, 59);

        LocalDateTime now = null;
        if(state != null && (state.equals("진행중") || state.equals("완료")))
            now = LocalDateTime.now();

        List<Rorder> rorderList = rorderRepository.findRordersByConditions(startDate, endDate, orderId, state, now, customerName, productName, startDeadline, endDeadLine);
        return RorderDto.of(rorderList);
    }

    //수주 등록 - 예정 납기일 예측을 위한 매개변수 변환
    public String calculateOrderDeliveryDate(String orderDateStr, String productId, String orderCntStr) {
        // String 타입 24시간제 형태 orderDateStr을 LocalDateTime 타입 orderDate으로 변환
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        LocalDateTime orderDate = LocalDateTime.parse(orderDateStr, inputFormatter);

        // 예정 납기일 받아서 String 형으로 반환
        LocalDateTime deliveryDate = mprocessService.caluculateDeadline(orderDate, productId, Long.parseLong(orderCntStr));
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd a HH:mm", Locale.ENGLISH);
        String deliveryStr = deliveryDate.format(outputFormatter).replace("AM", "오전").replace("PM", "오후");

        return deliveryStr;
    }

    //수주 등록
    public void addRorder(RorderFormDto dto) {
        dto.setId(generateId("ROD", "order_seq"));  //id 세팅
        rorderRepository.save(dto.toEntity());  //save하기
    }

    //수주 수정
    public void editRorder(RorderFormDto dto) {
        rorderRepository.save(dto.toEntity());
    }

    //id 지정하는 메소드
    @Transactional
    public String generateId(String head, String seqName) {
        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT " + seqName + ".NEXTVAL FROM dual").getSingleResult();
        String id = head + sequenceValue;
        return id;
    }

    //수주 정보 받아오기
    public RorderDto findById(String rid) {
        Optional<Rorder> optional = rorderRepository.findById(rid);
        return RorderDto.of(optional.get());
    }
}
