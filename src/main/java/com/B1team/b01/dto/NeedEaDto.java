package com.B1team.b01.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NeedEaDto {
    private double materialWeight;      //필요 원자재(g)
    private double liquidWeight;        //필요 액체(추출액, 농축액) 무게(g)
    private double amount;    //필요 개수(ea)
    private long box;                   //필요 박스(box)
}
