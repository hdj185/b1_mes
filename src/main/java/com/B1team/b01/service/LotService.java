package com.B1team.b01.service;


import com.B1team.b01.dto.LotDto;
import com.B1team.b01.dto.LotMakeNameDto;
import com.B1team.b01.entity.Finprod;
import com.B1team.b01.entity.LOT;
import com.B1team.b01.entity.Worder;

import com.B1team.b01.repository.LotRepository;
import com.B1team.b01.repository.LotSpecifications;

import com.B1team.b01.repository.WorderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;


@RequiredArgsConstructor
@Service
public class LotService {

    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private WorderRepository worderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    //문자열 시퀀스 메소드
    public String makeStringId() {
        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT lot_seq.NEXTVAL FROM dual").getSingleResult();
        String id = "LOT" + sequenceValue;
        return id;
    }

    //LotList 전체 보여보기
    public List<LOT> getLotList(){
        System.out.println(lotRepository.findAll());
        return lotRepository.findAll();
    }

    //list 검색
    public List<LOT> search(String id, String processId, String productId, LocalDateTime min, LocalDateTime max){

            Specification<LOT> specification = Specification.where(null);

            if(id != null){
                specification = specification.and(LotSpecifications.searchId(id));
            }
            if(processId != null){
                specification = specification.and(LotSpecifications.searchProcessId(processId));
            }
            if(productId != null){
                specification = specification.and(LotSpecifications.searchProductId(productId));
            }
            if(min !=null || max !=null){
                specification = specification.and(LotSpecifications.searchDate(min, max));
            }
            return lotRepository.findAll(specification);
    }



    //Lot dto 만들기
    public LotDto ruleProductName(String processId, String wplanId, String productId, Worder worder){
//        List<Worder> someWorder = worderRepository.findByState(processId, wplanId); // 작업지시테이블의 특정 1행을 불러옴
        List<Finprod> someFinprod = lotRepository.findByFinprodId(productId); // 작업지시테이블의 특정 1행을 불러옴

        //LOT코드
        //LOT코드 만들기(품목+공정코드+공정완료날짜)
        LotDto lotDto = new LotDto();
        String productName = worder.getProductId();  //품목코드

        LotMakeNameDto lotMakeNameDto= new LotMakeNameDto();
        switch (productName) {
            case "p21": lotMakeNameDto.setProduct("Y"); break;
            case "p22": lotMakeNameDto.setProduct("B"); break;
            case "p23": lotMakeNameDto.setProduct("S"); break;
            case "p24": lotMakeNameDto.setProduct("M"); break;
        }

        String processName  = worder.getProcessId(); //공정과정
        lotMakeNameDto.setProcess(processName);

        LocalDateTime finishDate = worder.getFinishDate();   //공정완료날짜
        String finishDateFome = finishDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        lotMakeNameDto.setDate(finishDateFome);

        //품목, 공정코드, 공정끝난시간
        String productCode = lotMakeNameDto.getProduct();
        String processCode  = lotMakeNameDto.getProcess();
        String DateCode  = lotMakeNameDto.getDate();


        //시간 포맷 변경
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //String finishDateFome2 = finishDate.format(formatter);
        //LocalDateTime finishDateFomeEnd = LocalDateTime.parse(finishDateFome2, formatter);


        //dto에 값 넣기
        lotDto.setProductId(productName);
        lotDto.setProcessId(processName);
        lotDto.setDate(finishDate);
        lotDto.setCode(productCode + processCode + DateCode);
        lotDto.setId(makeStringId());
        lotDto.setWorderId(worder.getId());
//        lotDto.setFinprodId(someFinprod.get(0).getId()); //완제품 고유번호
        lotDto.setOrderId(lotRepository.findByPlanOrderId(wplanId).getOrderId()); //수주 고유번호

        return lotDto;
    }

    //공정이 끝날 경우, LOT번호 추가하는 메소드
    public void createLotRecode(String processId, String wplanId, String productId, Worder worder){
        //String checkWorder = worderService.checkWorder(processId, wplanId); //공정고유번호를 체크해서 현재 가동상태가 완료인 것(=작업완료시간이 현재시간 이전이면)
        System.out.println("checkWorder");


        //if(checkWorder == "완료"){
            LotDto result  = ruleProductName(processId, wplanId, productId, worder);
            lotRepository.save(result.toEntity());
        //}else{
        //    System.out.println("등록실패");
        //}
    }


}
