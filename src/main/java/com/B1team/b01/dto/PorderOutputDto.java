package com.B1team.b01.dto;

import com.B1team.b01.config.CustomModelMapper;
import com.B1team.b01.entity.Porder;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PorderOutputDto {
    public String id;
    public String orderDate;
    public String arrivalDate;
    public String customerId;
    public String customerName;
    public Long cnt;
    public String mtrUnit;
    public Long price;
    public Long amount; //발주 금액
    public String state;
    public String mtrId;
    public Long mtrPrice;
    public String mtrName;

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    //Porder 엔티티를 RorderDto로 변환해주는 메소드
    public static PorderOutputDto of(Porder porder) {
        PorderOutputDto dto = CustomModelMapper.getModelMapper().map(porder, PorderOutputDto.class);
        dto.setState(LocalDateTime.now().isBefore(porder.getArrivalDate()) ? "예정" : "완료");
        return dto;
    }

    //List<Porder>를 List<PorderDto>로 변환해주는 메소드
    public static List<PorderOutputDto> of(List<Porder> entitys) {
        List<PorderOutputDto> dtos = new ArrayList<>();
        for(Porder entity : entitys)
            dtos.add(PorderOutputDto.of(entity));
        return dtos;
    }

    //RorderDto를 Order 엔티티로 변환해주는 메소드
    public Porder toEntity() {
        return modelMapper.map(this, Porder.class);
    }

}
