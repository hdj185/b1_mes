package com.B1team.b01.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NeedOrderDto {
    private String mtrId;
    private Double amount;  //그램(g) 단위로 들어감
}
