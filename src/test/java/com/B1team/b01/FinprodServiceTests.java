package com.B1team.b01;

import com.B1team.b01.service.FinfoService;
import com.B1team.b01.service.FinprodService;
import com.B1team.b01.service.WplanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class FinprodServiceTests {

    @Autowired
    FinprodService finprodService;

    @Rollback(false)
    @Test
    //완제품 정보 등록하기
    public void testResister(){

        finprodService.insertFinprod("ROD38");

    }

    @Test
    public void 월별생산량테스트() {
        List<Long> list = finprodService.getMonthlyList();
        for(int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "월 = " + list.get(i));
        }
    }
}
