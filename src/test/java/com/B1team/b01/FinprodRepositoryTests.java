package com.B1team.b01;

import com.B1team.b01.dto.ChartSumDTO;
import com.B1team.b01.dto.ShipmentDto;
import com.B1team.b01.entity.Finprod;
import com.B1team.b01.repository.FinprodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class FinprodRepositoryTests {
    @Autowired FinprodRepository finprodRepository;

    @Test
    void 테스트() {
        List<ShipmentDto> list = finprodRepository.findShipmentsByConditions(null, null, null, null, null, LocalDateTime.now());
        System.out.println("LocalDateTime.now()=" + LocalDateTime.now());
        for(ShipmentDto dto : list)
            System.out.println(dto);
    }

    @Test
    void 월별생산량테스트() {
        List<ChartSumDTO> list = finprodRepository.getMonthlyEaSum();
        for(ChartSumDTO dto : list)
            System.out.println(dto);
    }

    @Test
    void 완제품리스트테스트() {
        List<Finprod> list = finprodRepository.findAll();
        for(Finprod finprod : list)
            System.out.println(finprod);
    }
}
