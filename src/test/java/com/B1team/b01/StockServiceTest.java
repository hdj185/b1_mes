package com.B1team.b01;

import com.B1team.b01.service.StockService;
import com.B1team.b01.entity.Stock;
import com.B1team.b01.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StockServiceTest {
    @Autowired private StockRepository stockRepository;

    @Autowired private StockService stockService;

//    @Test
 //   void stTest() {
//        Optional<Stock> optional = stockRepository.findById("mtr38");
//        Stock stock = optional.get();

//        List<Stock> stockList = stockRepository.findByProductIdNotNull();
//        for (Stock stock : stockList) {
//
//            Long stockEa = stock.getEa();
//            System.out.println("개수" + stockEa);
//
//        }

//        stockService.stockCheck("p21","10");
//        System.out.println("d");

//        stockService.getProductStock();

//    }

//재고에서 원자재 빼기(작업실행되면)
    @Test
    void testDeleteStockEa(){
        stockService.deleteStockEa("p21", 2);

    }

/*    @Test
    void selectTestDeleteStockEa(){
        List<Long> result = stockRepository.findByMTRStockCnt();
        System.out.println(result);

    }
*/
}