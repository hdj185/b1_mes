package com.B1team.b01;

import com.B1team.b01.service.MaterialsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class MaterialsServiceTests {
    @Autowired private MaterialsService materialsService;

    @Test
    void 입고날짜계산테스트() {
        //현재 시간 발주 시 입고 날짜
        LocalDateTime currentTime = LocalDateTime.now();
        String[] arr = { "양배추", "흑마늘", "석류농축액", "매실농축액", "콜라겐", "파우치", "스틱파우치", "포장Box" };
        String msg = "";
        for(int i = 0; i < arr.length; i++)
            msg += arr[i] + " 입고 일자 : " + materialsService.calculateArrivalDate(LocalDateTime.now(), "MTR" + (i + 27)) + "\n";

        System.out.println("==========================================\n");
        System.out.println("현재 시간 : " + currentTime);
        System.out.println(msg);
        System.out.println("==========================================");
    }
}
