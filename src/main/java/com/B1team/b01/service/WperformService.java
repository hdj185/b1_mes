package com.B1team.b01.service;

import com.B1team.b01.dto.WperformDto;
import com.B1team.b01.entity.Wperform;
import com.B1team.b01.entity.Wplan;
import com.B1team.b01.repository.WperformRepository;
import com.B1team.b01.repository.WperformSpecifications;
import com.B1team.b01.repository.WplanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WperformService {
    @Autowired
    private final WperformRepository wperformRepository;
    @Autowired
    private final WplanRepository wplanRepository;

    @PersistenceContext
    private EntityManager entityManager;

    //문자열 시퀀스 메소드

    public String makeStringId() {
        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT wperform_seq.NEXTVAL FROM dual").getSingleResult();
        String id = "WPER" + sequenceValue;
        return id;

    }

    //작업실적 작성하기

    public List<Wplan> insertWperform(String orderId) {

        //작업실적을 작성할 작업계획이 있는지 조회(작업계획 테이블 중 '완료'가 들어가 있는 계획 행)(작업계획 고유번호를 전송받아서)
        List<Wplan> result = wplanRepository.findByWplanState(orderId);

        //작업계획 테이블에 상태가 "완료"인 작업계획이 있다면 작업실적 테이블에 해당 실적 등록하기(또는 업데이트)
        WperformDto wperformDto = new WperformDto();
        if (result != null) {

            for(int i=0; i<result.size(); i++){
                Wplan firstWplan = result.get(i);
                wperformDto.setId(makeStringId()); //문자열 시퀀스 추가
                wperformDto.setWplanId(firstWplan.getId());
                wperformDto.setProductId(firstWplan.getProductId());
                wperformDto.setOrderId(firstWplan.getOrderId());
                wperformDto.setCnt(firstWplan.getCnt());
                wperformDto.setStartDate(firstWplan.getStartDate());
                wperformDto.setEndDate(firstWplan.getEndDate());

                System.out.println("작업실적 조회: "+ wperformDto);
                wperformRepository.save((wperformDto.toEntity()));
            }

        }

        return result;
    }

    public List<Wperform> search(String productId, String orderId, LocalDateTime min, LocalDateTime max){
        Specification<Wperform> specification = Specification.where(null);

        if(productId != null){
            specification = specification.and(WperformSpecifications.searchProductId(productId));
        }
        if(orderId != null){
            specification = specification.and(WperformSpecifications.searchOrderId(orderId));
        }
        if(min != null || max != null){
            specification = specification.and(WperformSpecifications.searchDate(min,max));
        }

        return  wperformRepository.findAll(specification);
    }

    public List<Wperform> getAllWperform() {
        return wperformRepository.findAll();
    }
}

