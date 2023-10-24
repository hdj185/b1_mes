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
public class FacilityStatusDto {
    String name;
    String state;
    String id;

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    public static FacilityStatusDto of(Finfo finfo) {
        return CustomModelMapper.getModelMapper().map(finfo, FacilityStatusDto.class);
    }

    public static List<FacilityStatusDto> of(List<Finfo> entitys) {
        List<FacilityStatusDto> dtos = new ArrayList<>();
        for(Finfo entity : entitys) {
            dtos.add(of(entity));
        }
        return dtos;
    }
}
