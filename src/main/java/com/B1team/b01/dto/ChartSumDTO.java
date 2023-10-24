package com.B1team.b01.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartSumDTO {
    private int category;
    private long sumEa;
}
