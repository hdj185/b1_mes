package com.B1team.b01.dto;

import com.B1team.b01.config.CustomModelMapper;
import com.B1team.b01.entity.Finfo;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinfoDto {
    private String id;        //설비 고유번호
    private String name;    //자재명
    private String capa;    //생산가능량
    private String company;    //제조사
    private String model;    //모델명
    private String location;    //설비위치
    private String inspectionDate;    //최근 점검 일자
    private Long leadtime;    //생산 준비 시간

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    //Finfo 엔티티를 FinfoDto로 변환해주는 메소드 (LocalDateTime -> String 변환)
    public static FinfoDto of(Finfo finfo) {
        return CustomModelMapper.getModelMapper().map(finfo, FinfoDto.class);
    }

    //List<Finfo>를 List<FinfoDto>로 변환해주는 메소드
    public static List<FinfoDto> of(List<Finfo> entitys) {
        List<FinfoDto> dtos = new ArrayList<>();
        for(Finfo entity : entitys) {
            dtos.add(of(entity));
        }
        return dtos;
    }

}
