package com.B1team.b01.dto;

import com.B1team.b01.config.CustomModelMapper;
import com.B1team.b01.entity.Rorder;
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
public class RorderDto {

    public String id;
    public String date;
    public String customerId;
    public String customerName;
    public String deadline;
    public Long price;
    public String state;
    public String changedate;
    public String productId;
    public Long cnt;
    public String productName;


    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑



    //Order 엔티티를 RorderDto로 변환해주는 메소드
    public static RorderDto of(Rorder rorder) {
        RorderDto dto = CustomModelMapper.getModelMapper().map(rorder, RorderDto.class);
        switch(dto.getState()) { //Rorder 엔티티의 state = 확정, 미확정
            case "미확정" : dto.setState("진행전"); break;
            case "확정" :
                //deadline이 현재 시간보다 이후이면
                if(rorder.getDeadline().isAfter(LocalDateTime.now()))
                    dto.setState("진행중");
                else
                    dto.setState("완료");
                break;
        }
        return dto;
    }

    //RorderDto를 Order 엔티티로 변환해주는 메소드
    public Rorder toEntity() {
        return modelMapper.map(this, Rorder.class);
    }




    // List<Rorder>를 List<RorderDto>로 변환해주는 메소드
    public static List<RorderDto> of(List<Rorder> rorders) {
        List<RorderDto> rorderDtos = new ArrayList<>();
        for (Rorder rorder : rorders) {
            rorderDtos.add(of(rorder));
        }
        return rorderDtos;
    }

    //List<RorderDto>를 List<Rorder>로 변환해주는 메소드
    public static List<Rorder> toEntity(List<RorderDto> dtoList) {
        List<Rorder> entitys = new ArrayList<>();
        for (RorderDto dto : dtoList) {
            entitys.add(dto.toEntity());
        }
        return entitys;
    }
}