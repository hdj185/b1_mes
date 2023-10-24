package com.B1team.b01.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@ToString
@NoArgsConstructor
public class PinoutOutputDto {
    String id;          //pinout id
    String sort;        //입출고 구분(입고/출고)
    String date;        //입출고일
    String mtrName;        //원자재명
    Long cnt;           //수량
    String unit;        //단위
    String location;    //창고위치

    public PinoutOutputDto(String id, String sort, LocalDateTime date, String mtrName, Long cnt, String unit, String location) {
        this.id = id;
        this.sort = sort;
        this.date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.mtrName = mtrName;
        this.cnt = cnt;
        this.unit = unit;
        this.location = location;
    }
}
