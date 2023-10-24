package com.B1team.b01;

import com.B1team.b01.dto.WorderDto;
import com.B1team.b01.dto.WplanDto;
import com.B1team.b01.entity.Wplan;
import com.B1team.b01.service.WperformService;
import com.B1team.b01.service.WplanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class WperformServiceTests {
    @Autowired private WperformService wperformService;
    @Autowired private WplanService wplanService;


    //작업실적 등록하기
    @Test
    public void testInsertWperform(){
        List<Wplan> see =  wperformService.insertWperform("12"); //수주번호가 12고 계획이 완료인 상태 = insert됨
    }
    

}
