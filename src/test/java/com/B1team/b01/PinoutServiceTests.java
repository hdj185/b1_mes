package com.B1team.b01;

import com.B1team.b01.dto.PinoutOutputDto;
import com.B1team.b01.service.PinoutService;
import com.B1team.b01.dto.PinoutDto;
import com.B1team.b01.entity.Pinout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class PinoutServiceTests {
    @Autowired private PinoutService pinoutService;


    //자재출고 등록하기 테스트
 /*   @Test
    public void testRegisterPinout(){

        String wplanId = "WPLAN12";

        PinoutDto pinoutDto = PinoutDto.builder()
                .mtrId("MTR36") // (임시) 원자재 고유번호 *
                .productCnt(30L) // 출고 수량
                .productDate(LocalDateTime.now()) // (임시) 출고날짜 = 작업지시 날짜*
                .sort("출고")
                .build(); // 출고수량

           pinoutService.createInout(pinoutDto);

    }
*/

/*
    @Test
    public void testOneBoxNeedProduct(){

           Long[] result = pinoutService.oneBoxNeedProduct("p21");
           System.out.println(Arrays.toString(result));
           System.out.println(result[1]);
    }
*/

    //자재출고 등록하기 테스트2
    @Test
    public void testRegisterPinout2(){

        //Long[] result = pinoutService.oneBoxNeedProduct("p21");
        //System.out.println(Arrays.toString(result));
        //System.out.println(result[1]);

        pinoutService.createMTROut("ROD38", "p24");
    }

    //입출고 현황 리스트 뽑기 테스트
    @Test
    public void getListTest() {
        List<PinoutOutputDto> list = pinoutService.getPinoutList(null, null, null, null);
        System.out.println(list);
    }
}
