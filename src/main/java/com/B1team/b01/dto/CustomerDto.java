package com.B1team.b01.dto;

import com.B1team.b01.entity.Customer;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private String id;
    private String sort;
    private String name;
    private String address;
    private String tel;
    private String businessNumber;
    private String fax;

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑


    //Customer 엔티티를 CustomorDto 변환해주는 메소드
    public static CustomerDto of(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    //List<Customer>를 List<CustomorDto>로 변환해주는 메소드
    public static List<CustomerDto> of(List<Customer> entitys) {
        List<CustomerDto> dtos = new ArrayList<>();
        for(Customer entity : entitys) {
            dtos.add(of(entity));
        }
        return dtos;
    }
}
