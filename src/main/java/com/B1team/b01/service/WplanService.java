package com.B1team.b01.service;


import com.B1team.b01.dto.WorderDto;
import com.B1team.b01.dto.WplanDto;

import com.B1team.b01.entity.Wplan;
import com.B1team.b01.repository.RorderRepository;
import com.B1team.b01.repository.StockRepository;
import com.B1team.b01.repository.WplanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service

public class WplanService {
    @Autowired
    private final WplanRepository wplanRepository;
    private final RorderRepository rorderRepository;
    private final StockRepository stockRepository;

    @Autowired
    private final MprocessService mprocessService;
    @Autowired
    private final ProductionService productionService;

    private Wplan wplan;


    private final LocalDateTime ifNow = LocalDateTime.now();


    @PersistenceContext
    private EntityManager entityManager;


    //작성계획 등록메소드
    @Transactional
    public void createWplan(LocalDateTime materialReadyDate, String productId, long orderCnt, String orderId) {
        //orderDate : 수주 날짜(시작 날짜), productId : 품목id, orderCnt: 수주량(box)

        //수주고유번호로 계획이 세워져 있는지 확인  //wplan table 조회
        Optional<Wplan> confirmWplan = wplanRepository.findByPlanOrderId(orderId);

        //계획 세워져 있는게 없다면, 작업계획 등록하기
        //계획 시작일자 전에는 진행대기로 표시
        if (confirmWplan.isPresent()) {
            System.out.println("세워져 있는계획이 있습니다");
        } else {
            WplanDto wplanDto = new WplanDto();
            List<WorderDto> worderDtos = mprocessService.calculateWorderDate(materialReadyDate, productId, orderCnt);

            wplanDto.setId(generateWplanId()); //문자열 시퀀스
            wplanDto.setCnt(productCnt(productId, orderId)); //생산 수량
            wplanDto.setStartDate(worderDtos.get(0).getStartDate()); //자재 준비 완료 시간
            wplanDto.setEndDate(worderDtos.get(worderDtos.size() - 1).getFinishDate()); //생산완료시간(=포장완료시간)
            wplanDto.setOrderId(orderId); //수주 고유번호
            wplanDto.setProductId(productId); // 품목명

            if (ifNow.isBefore(worderDtos.get(0).getStartDate())) {
                wplanDto.setState("진행대기");
            } else if (ifNow.isEqual(worderDtos.get(0).getStartDate()) || ifNow.isAfter(worderDtos.get(0).getStartDate())) {
                wplanDto.setState("진행중");
            } else {
                wplanDto.setState("완료");
            }//계획진행상태
            wplanRepository.save(wplanDto.toEntity());
        }
    }


    //문자열 시퀀스 메소드
    public String generateWplanId() {
        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT wplan_seq.NEXTVAL FROM dual").getSingleResult();
        String id = "WPLAN" + sequenceValue;
        return id;

    }

    //문자열 시퀀스 메소드
    public String generateId(String head, String seqName) {
        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT " + seqName + ".NEXTVAL FROM dual").getSingleResult();
        String id = head + sequenceValue;
        return id;

    }

    //생산필요량 구하기(수주량-재고량)
    public Long productCnt(String productId, String orderId) {

        Long orderCnt = rorderRepository.findByOrderCnt(orderId);//수주량
        Long stockCnt = stockRepository.findByStockCnt(productId) == null ? 0 : stockRepository.findByStockCnt(productId);//재고량


        System.out.println(orderCnt);
        System.out.println(stockCnt);

        Long unit = Long.valueOf(productId.equals("p21") || productId.equals("p22") ? 30 : 25);
        Long orderCntUnit = orderCnt * unit;

        System.out.println("WplanService orderCntUnit=" + orderCntUnit);
        System.out.println("WplanService stockCnt=" + stockCnt);
        //수주량 - 재고량
        Long result = orderCntUnit - stockCnt;
        System.out.println("생산필요량" + result);
        return result;
    }

    // 공정 돌아가고 있는지 확인 (생산계획일자 기준으로 계산)
    public LocalDateTime getWplansByWorkDate(LocalDateTime resultDate) {

        //작업시간 계산 불러옴
        List<WplanDto> getResultDate = wplanRepository.workDate(resultDate);

        System.out.println(resultDate + "resultDate");

        if(getResultDate.isEmpty()) { //쿼리 값이 없다면 공정 안 돌아가고있음
//            LocalDateTime workDate = LocalDateTime.parse(getResultDate.toString(),
//                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            System.out.println("현재 공정 비어있음");

            return resultDate;

        }else{ //쿼리 값이 있다면 현재 공정 돌아가고 난 후에 돌릴 수 있음
            WplanDto Date = getResultDate.get(0);
            LocalDateTime endDateValue = Date.getEndDate();

            long resultResultDate = ChronoUnit.NANOS.between(resultDate, endDateValue); //long

            System.out.println("공정 endDate - 계산Date : " + resultResultDate + "초 resultResultDate");
            LocalDateTime resultDateTime = endDateValue.plusNanos(resultResultDate); //dateTime

            System.out.println("최종 일자 : " + resultDateTime + "resultDateTime");

            return resultDateTime;
        }
    }

    //계획상태 업데이트(*)
    @Transactional
    public List<LocalDateTime> updateState() {

        List<LocalDateTime> startTime = wplanRepository.selectStartTime();
        List<LocalDateTime> endTime = wplanRepository.selectEndTime();

        LocalDateTime currentTime;
        currentTime = LocalDateTime.now();

        for (int i = 0; i < startTime.size(); i++) {
            if (currentTime.isBefore(startTime.get(i))) {
                wplanRepository.updateWaitState(startTime.get(i));

            } else if (currentTime.isAfter(endTime.get(i))) {
                wplanRepository.updateEndState(endTime.get(i));
            } else {
                wplanRepository.updateIngState(endTime.get(i));
            }
        }

        return startTime;
    }
}