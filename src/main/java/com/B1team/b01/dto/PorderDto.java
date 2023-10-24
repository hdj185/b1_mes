package com.B1team.b01.dto;

import com.B1team.b01.entity.Porder;
import com.B1team.b01.entity.Rorder;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PorderDto {
    public String id;
    public String unit;
    public Long cnt;
    public String customerId;
    public String customerName;
    public LocalDateTime orderDate;
    public Long price;
    public String state;
    public LocalDateTime arrivalDate;
    public String mtrId;
    public Long amount;
    public String name;

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    //Porder 엔티티를 RorderDto로 변환해주는 메소드
    public static PorderDto of(Porder porder) { return modelMapper.map(porder, PorderDto.class); }

    //List<Porder>를 List<PorderDto>로 변환해주는 메소드
    public static List<PorderDto> of(List<Porder> entitys) {
        List<PorderDto> dtos = new ArrayList<>();
        for(Porder entity : entitys)
            dtos.add(PorderDto.of(entity));
        return dtos;
    }

    //RorderDto를 Order 엔티티로 변환해주는 메소드
    public Porder toEntity() {
        return modelMapper.map(this, Porder.class);
    }

}
