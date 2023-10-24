package com.B1team.b01.service;


import com.B1team.b01.dto.PinoutDto;

import com.B1team.b01.dto.PinoutOutputDto;
import com.B1team.b01.dto.PorderDto;
import com.B1team.b01.entity.Pinout;
import com.B1team.b01.entity.Wplan;
import com.B1team.b01.repository.PinoutRepository;
import com.B1team.b01.repository.WplanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RequiredArgsConstructor
@Service
@Log4j2
public class PinoutService {
    @Autowired
    private final PinoutRepository pinoutRepository;
    @Autowired
    private final WplanRepository wplanRepository;

    @Autowired
    private final MaterialsService materialsService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    public Long[] productPackage = new Long[0];
    public String[] productContent = new String[0];

    @PersistenceContext
    private EntityManager entityManager;


    //문자열 시퀀스 메소드
    public String makeStringId() {
        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT pinout_seq.NEXTVAL FROM dual").getSingleResult();
        String id = "INOUT" + sequenceValue;
        return id;

    }

    //제품 필요 자재 단위 //1BOX 기준 필요량(g 기준)
    public Long[] oneBoxNeedProduct(String productId) {

        switch (productId) {
            case "p21":
                Long cabbage = 1500L;
                Long pouch = 30L;
                Long box = 1L;
                productPackage = new Long[]{cabbage, pouch, box};
                break;

            case "p22":
                Long blackgarlic = 250L;
                pouch = 30L;
                box = 1L;
                productPackage = new Long[]{blackgarlic, pouch, box};
                break;

            case "p23":
                Long pomegranate = 125L;
                Long collagen = 30L;
                Long stickpouch = 25L;
                box = 1L;
                productPackage = new Long[]{pomegranate, collagen, stickpouch, box};
                break;

            case "p24":
                Long plum = 125L;
                collagen = 50L;
                stickpouch = 25L;
                box = 1L;
                productPackage = new Long[]{plum, collagen, stickpouch, box};
                break;
        }
        return productPackage;
    }

    //productPackage
    public String[] productContent(String productId){
        switch (productId) {
            case "p21":
                productContent = new String[]{"MTR36", "MTR41", "MTR43"};
                break;
            case "p22":
                productContent = new String[]{"MTR37", "MTR41", "MTR43"};
                break;
            case "p23":
                productContent = new String[]{"MTR38", "MTR40", "MTR42", "MTR43"};
                break;
            case "p24":
                productContent = new String[]{"MTR39", "MTR40", "MTR42", "MTR43"};
                break;
        }
        return productContent;
    }


    //자재입출정보(출고-제품생산에 씀) 등록메소드
    public void createMTROut(String orderId, String productId) {
            // orderId = 수주일, orderDate = 발주 주문 시간
        //해당 작업계획 확인
        Wplan wplanInfo = wplanRepository.findByPlanOrderId2(orderId);

            Long[] oneBoxNeedProductResult = oneBoxNeedProduct(productId);  //출고수량
            String[] productContentResult = productContent(productId);      //출고 자재명
            System.out.println(oneBoxNeedProductResult);

            //반복 필요원자재 수만큼 저장
            for(int i=0; i < productPackage.length; i++){
                PinoutDto pinoutDto = new PinoutDto();
                pinoutDto.setId(makeStringId()); //입출고 고유번호
                pinoutDto.setSort("출고");       //구분
                pinoutDto.setMtrId(productContentResult[i]);      //자재 고유번호
                pinoutDto.setProductCnt(oneBoxNeedProductResult[i] * wplanInfo.getCnt());      //출고 수량
                pinoutDto.setProductDate(wplanInfo.getStartDate());     //출고일, 자재준비완료일 && 원료계량시작일
                pinoutRepository.save((pinoutDto.toEntity()));
            }

        //--조건
        //LocalDateTime readyDate = materialsService.calculateArrivalDate(orderDate, mtrId);  //동진님께 여쭤보기(mtrId)
        //if (wplanInfo.isPresent() && readyDate.isBefore(LocalDateTime.now())) {
        //수주번호에 해당하는 작업계획이 있고,
        //원자재가 충분하면서, 원자재 입고 날짜가 현재 시간 이전인 경우
        //입출정보(출고) 등록하기


    }

    //자재 입출 현황 조회 페이지 - 레포지토리 매개변수에 맞게 매개변수 타입 변환
    public List<PinoutOutputDto> getPinoutList(String sort,
                                               String start,
                                               String end,
                                               String mtrName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDate = start == null || "".equals(start)? null : LocalDate.parse(start, formatter).atStartOfDay();
        LocalDateTime endDate = end == null || "".equals(end) ? null : LocalDate.parse(end, formatter).atTime(23, 59, 59);
        return pinoutRepository.findPinoutsByConditions(sort, startDate, endDate, mtrName, LocalDateTime.now());
    }


    //문자열 시퀀스 메소드
    @Transactional
    public String generateId(String head, String seqName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT " + seqName + ".NEXTVAL FROM dual").getSingleResult();
        String id = head + sequenceValue;
        return id;
    }

    public void createPinout(PinoutDto pdto) {

        Pinout po = new Pinout();
        po.setId(generateId("PIN", "pinout_seq"));
        po.setMtrId(pdto.getMtrId());
        po.setProductCnt(pdto.getProductCnt());
        po.setProductDate(pdto.getProductDate());
        po.setSort(pdto.getSort());

        pinoutRepository.save(po);
    }

}