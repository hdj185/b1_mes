package com.B1team.b01.dto;


import com.B1team.b01.entity.Worder;
import com.B1team.b01.entity.Wplan;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorderDto {
    private String id;   //작업지시 고유번호
    private String processId; //공정 고유번호
    private String wplanId; //작업계획 고유번호
    private String facilityId; //설비정보 고유번호
    private LocalDateTime startDate; //작업 시작 일자
    private LocalDateTime finishDate; //작업 완료 일자
    private String productId; //품목명 고유번호


    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

    //Worder 엔티티를 WorderDto로 변환해주는 메소드
    public static WorderDto of(Worder worder) {
        return modelMapper.map(worder, WorderDto.class);
    }

    //WplanDto를 Wplan 엔티티로 변환해주는 메소드
    public Worder toEntity() {
        return modelMapper.map(this, Worder.class);
    }


    public WorderDto(String processId, String facilityId, LocalDateTime startDate, LocalDateTime finishDate) {
        this.processId = processId;
        this.facilityId = facilityId;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }


}
