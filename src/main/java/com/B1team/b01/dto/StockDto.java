package com.B1team.b01.dto;

import com.B1team.b01.entity.Materials;
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
public class StockDto {
    private String stockiId;
    private String productId;
    private String mtrId;
    private String location;
    private Long stockEa;
    private String stockUnit;

    //product
    private String name;
    private String sort;
    private Long price;

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    //Stock 엔티티를 stockDto 변환해주는 메소드
    public static StockDto of(Stock stock) {
        return modelMapper.map(stock, StockDto.class);
    }
    public static List<StockDto> of(List<Stock> entities){
        List<StockDto> sdto = new ArrayList<>();
        for(Stock entity : entities) {
            sdto.add(of(entity));

        }

        return sdto;
    }

    public void setProduct(Product product){
        this.setName(product.getName());
        this.setSort(product.getSort());
        this.setPrice(product.getPrice());
    }

    public List<StockDto> setProduct(List<StockDto> dtoList, List<Product> productList) {
        for(StockDto sdto : dtoList) {
            for(int i=0; i<productList.size();i++) {
                if(sdto.getProductId().equals(productList.get(i).getId())){
                    sdto.setProduct(productList.get(i));
                    productList.remove(i);
                    break;
                }
            }
        }
        return dtoList;
    }

}
