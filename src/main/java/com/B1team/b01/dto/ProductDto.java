package com.B1team.b01.dto;

import com.B1team.b01.entity.Product;
import com.B1team.b01.entity.Rorder;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;        //제품 고유번호
    private String name;    //제품명
    private Long price;    //가격
    private String sort;    //분류
    private String location;    //분류

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    //Product 엔티티를 ProductDto 변환해주는 메소드
    public static ProductDto of(Product product) { return modelMapper.map(product, ProductDto.class); }

    //List<Product>를 List<ProductDto>로 변환해주는 메소드
    public static List<ProductDto> of(List<Product> entitys) {
        List<ProductDto> dtos = new ArrayList<>();
        for(Product entity : entitys) {
            dtos.add(of(entity));
        }
        return dtos;
    }

}
