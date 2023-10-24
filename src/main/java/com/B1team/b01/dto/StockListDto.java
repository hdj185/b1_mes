package com.B1team.b01.dto;

import com.B1team.b01.entity.Product;
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
public class StockListDto {
    //stock
//    private String id;
    private String stockId;
    private String productId;
    private String mtrId;
    private Long ea;
    private String unit;

    //product
    private String name;
    private String sort;
    private Long price;
    private String location;

}
