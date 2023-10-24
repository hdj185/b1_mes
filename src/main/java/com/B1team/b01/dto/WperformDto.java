package com.B1team.b01.dto;

import com.B1team.b01.entity.Wperform;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WperformDto {

    private String id;        //작업실적 고유번호
    private String wplanId; //작업계획 고유번호
    private String productId; //제품 고유번호
    private String orderId; //수주 고유번호
    private LocalDateTime startDate;    //작업시작일자
    private Long cnt;   //수량
    private LocalDateTime endDate;  //작업완료일자

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    //Wperform 엔티티를 WperformDto로 변환해주는 메소드
    public static WperformDto of(Wperform wperform) {
        return modelMapper.map(wperform, WperformDto.class);
    }

    //WperformDto를 Wperform 엔티티로 변환해주는 메소드
    public Wperform toEntity() {
        return modelMapper.map(this, Wperform.class);
    }
}
