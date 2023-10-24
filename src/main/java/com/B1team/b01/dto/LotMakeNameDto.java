package com.B1team.b01.dto;

import com.B1team.b01.entity.LOT;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LotMakeNameDto {


    private String product; //제품명
    private String process;  //공정과정
    private String date;  //공정완료 날짜( ':' 제외)

}
