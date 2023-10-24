package com.B1team.b01.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailDto {
    private String id;
    private String porderId;
    private String mtrId;
    private Long detailCnt;
    private Long detailPrice;

}
