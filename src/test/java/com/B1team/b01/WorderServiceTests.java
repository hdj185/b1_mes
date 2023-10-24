package com.B1team.b01;

import com.B1team.b01.service.WorderService;
import com.B1team.b01.dto.WorderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class WorderServiceTests {
    @Autowired private WorderService worderService;


    //작업지시 등록하기
    @Test
    public void testdoWorder(){

        LocalDateTime materialReadyDate = LocalDateTime.of(2023, 5, 25, 18, 0);
        worderService.doWorder("ROD29",materialReadyDate,"p21", 1);
    }

}
