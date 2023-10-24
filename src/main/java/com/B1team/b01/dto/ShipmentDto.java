package com.B1team.b01.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@ToString
@NoArgsConstructor
public class ShipmentDto {
    public String id;                              //완제품 고유번호
    public String deliveryDate;      //출하일
    public String productName;              //품목명
    public Long ea;                         //수량
    public String orderId;                  //수주 고유번호
    public String customerName;             //거래처명

    public ShipmentDto(String id, LocalDateTime deliveryDate, String productName, Long ea, String orderId, String customerName) {
        this.id = id;
        this.deliveryDate = deliveryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.productName = productName;
        this.ea = ea;
        this.orderId = orderId;
        this.customerName = customerName;
    }
}
