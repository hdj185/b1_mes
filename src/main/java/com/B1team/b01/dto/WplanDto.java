package com.B1team.b01.dto;

import com.B1team.b01.entity.Materials;
import com.B1team.b01.entity.Wplan;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import java.time.LocalDateTime;
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WplanDto {

    private String id;   //작업계획 고유번호
    private String productId; //제품 고유번호
    private String orderId; //수주 고유번호
    private Long cnt; //계획수량
    private LocalDateTime startDate; //계획 시작 일자
    private LocalDateTime endDate; //계획 완료 일자
    private String state; //작업 진행 상태

    private LocalDateTime calculDate; //계산 일자

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    //Wplan 엔티티를 WplanDto로 변환해주는 메소드
    public static WplanDto of(Wplan wplan) {
        return modelMapper.map(wplan, WplanDto.class);
    }

    //WplanDto를 Wplan 엔티티로 변환해주는 메소드
    public Wplan toEntity() {
        return modelMapper.map(this, Wplan.class);
    }

}
