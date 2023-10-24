package com.B1team.b01;

import com.B1team.b01.dto.LotDto;
import com.B1team.b01.entity.LOT;
import com.B1team.b01.entity.Wplan;
import com.B1team.b01.service.LotService;
import com.B1team.b01.service.WperformService;
import com.B1team.b01.service.WplanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class LotServiceTests {
    @Autowired
    private LotService lotService;

/*
    //작업실적 등록하기
    @Test
    public void testCreateLotRecode(){
        lotService.createLotRecode("A110", "WPLAN39"); //

    }
*/
/*
    //조회
    @Test
    public void testruleProductName(){
        lotService.ruleProductName("A102", "WPLAN35");
    }
*/

    //LOT 등록
    @Test
    @Rollback(false)
    public void testCreateLotRecode(){
/*
        lotService.createLotRecode("A101", "WPLAN39","p24");
        lotService.createLotRecode("A102", "WPLAN39","p24");
        lotService.createLotRecode("A103", "WPLAN39","p24");
        lotService.createLotRecode("A104", "WPLAN39","p24");
        lotService.createLotRecode("A105", "WPLAN39","p24");
        lotService.createLotRecode("A106", "WPLAN39","p24");
        lotService.createLotRecode("A107", "WPLAN39","p24");
        lotService.createLotRecode("A108", "WPLAN39","p24");
        lotService.createLotRecode("A109", "WPLAN39","p24");
        lotService.createLotRecode("A100", "WPLAN39","p24");
        lotService.createLotRecode("A110", "WPLAN39","p24");
*/
    }

}
