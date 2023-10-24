package com.B1team.b01.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BomListDto {
    //bom
    private String id;
    private String mtrId;
    private String productId;
    private String mtrName;
    private Long volume;
    //product
    private String productName;

}
