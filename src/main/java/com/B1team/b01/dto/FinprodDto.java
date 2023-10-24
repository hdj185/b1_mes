package com.B1team.b01.dto;

import com.B1team.b01.entity.Customer;
import com.B1team.b01.entity.Finprod;
import com.B1team.b01.entity.LOT;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinprodDto {
    private String id;
    private String productId;    //제품 고유번호
    private String orderId;    //수주 고유번호
    private Long ea;    //완제품 수량
    private LocalDateTime deadline;    //납품예정일
    private String product;    //품목명

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    //Customer 엔티티를 CustomorDto 변환해주는 메소드
    public static FinprodDto of(Finprod finprod) {
        return modelMapper.map(finprod, FinprodDto.class);
    }

    //LotDto를 LOT 엔티티로 변환해주는 메소드
    public Finprod toEntity() {
        return modelMapper.map(this, Finprod.class);
    }

}
