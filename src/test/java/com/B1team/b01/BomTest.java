package com.B1team.b01;

import com.B1team.b01.service.BomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BomTest {
    @Autowired
    private BomService bomService;

    @Test
    void finaTest(){
        System.out.println(bomService.calcBom("p21",1));
    }
}