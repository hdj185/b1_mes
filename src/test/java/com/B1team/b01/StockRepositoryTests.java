package com.B1team.b01;

import com.B1team.b01.dto.MaterialStockDto;
import com.B1team.b01.entity.Stock;
import com.B1team.b01.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StockRepositoryTests {
    @Autowired
    private StockRepository stockRepository;

    @Test
    void 원자재재고리스트불러오기() {
        List<MaterialStockDto> list = stockRepository.findMaterialStock("양배추");
        for(MaterialStockDto stock : list)
            System.out.println(stock);
    }
}
