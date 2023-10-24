package com.B1team.b01.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialStockDto {
    private String name;    //원자재명
    private Long ea;        //재고량
    private String unit;    //단위
}
