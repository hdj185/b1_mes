package com.B1team.b01.service;

import com.B1team.b01.entity.Materials;
import com.B1team.b01.repository.MaterialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MaterialsService {
    private final MaterialsRepository materialsRepository;


    //시뮬레이션 - 발주 입고 날짜 계산
    public LocalDateTime calculateArrivalDate(LocalDateTime orderDate, String materialId) {
        //매개변수 orderDate : 발주 주문 시간 / materialId : 원자재 고유 번호
        Optional<Materials> optional = materialsRepository.findById(materialId);
        Materials materials = optional.get();
        //TODO : optional이 비었을 시(nullException) 처리

        //실질 발주 주문 날짜 (발주 처리 기준 시간 이전에 발주 시 당일, 이후 발주 시 다음날)
        LocalDateTime date = orderDate.getHour() < materials.getCutoffTime() ? orderDate : orderDate.plusDays(1);

        //리드타임(발주 소요 시간) 더하기
        date = date.plusDays(materials.getLeadtime());

        //주말 판정
        if(date.getDayOfWeek().getValue() > 5)
            date = date.plusDays(2);

        //입고일자 화, 목이면 다음날로 미루기 (입고일자는 월, 수, 금)
        if(date.getDayOfWeek().getValue() == 2 || date.getDayOfWeek().getValue() == 4)
            date = date.plusDays(1);

        //입고 시간 오전 10시로 고정
        date = date.withHour(10).withMinute(0).withSecond(0).withNano(0);

        return date;
    }


}
