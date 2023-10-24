package com.B1team.b01;

import com.B1team.b01.dto.WplanDto;
import com.B1team.b01.repository.RorderRepository;
import com.B1team.b01.service.WplanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest
public class WplanServiceTests {
    @Autowired
    private WplanService wplanService;


    //문자열 시퀀스 확인하기
    //@Test
    @Transactional
/*    @Test
    public void testGenerateWplanId() {
        String wplanId = wplanService.generateWplanId();
        System.out.println("Generated Wplan ID: " + wplanId);
    }
*/
/*
    //생산량 구하기
    @Test
    public void testproductCnt() {
       wplanService.productCnt("p21", "ROD29");
    }
     // LocalDateTime materialReadyDate = LocalDateTime.of(2023, 5, 29, 9, 0);
       // wplanService.createWplan(materialReadyDate, "p24", 1, "ROD41");
*/

    //작업계획 등록하기
    @Rollback(false)
    @Test
    public void testResister(){

        LocalDateTime materialReadyDate = LocalDateTime.of(2023, 5, 29, 9, 0);
        wplanService.createWplan(materialReadyDate, "p21", 1, "ROD65");

    }

    @Test
    public void test() {

        LocalDateTime d = LocalDateTime.of(LocalDate.parse("2023-05-26"), LocalTime.parse("23:59:59"));

        System.out.println(wplanService.getWplansByWorkDate(d) +"dd2하하");

    }

}
