package com.B1team.b01.dto;

import com.B1team.b01.entity.Materials;
import com.B1team.b01.entity.Stock;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDeleteDto {
    //stock
    private String id;
    private String location;
    private String mtrId;
    private Long ea;
    private String unit;

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑


    public static StockDeleteDto of(Stock stock) {
        return modelMapper.map(stock, StockDeleteDto.class);
    }

    public Stock toEntity() {
        return modelMapper.map(this, Stock.class);
    }


}
