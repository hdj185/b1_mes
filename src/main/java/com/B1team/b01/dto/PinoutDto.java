package com.B1team.b01.dto;

import com.B1team.b01.entity.Pinout;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PinoutDto {
    public String id;
    public String mtrId;
    public Long productCnt;
    public LocalDateTime productDate;
    public String sort;


    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    //Pinout 엔티티를 PinoutDto로 변환해주는 메소드
    public static PinoutDto of(Pinout pinout) {
        return modelMapper.map(pinout, PinoutDto.class);
    }

    //PinoutDto를 Pinout 엔티티로 변환해주는 메소드
    public Pinout toEntity() {
        return modelMapper.map(this, Pinout.class);
    }
}
