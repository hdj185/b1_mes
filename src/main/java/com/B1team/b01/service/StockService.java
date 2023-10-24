package com.B1team.b01.service;

import com.B1team.b01.dto.PorderDto;
import com.B1team.b01.dto.StockDeleteDto;
import com.B1team.b01.dto.StockDto;
import com.B1team.b01.dto.StockListDto;
import com.B1team.b01.entity.*;
import com.B1team.b01.repository.PorderRepository;
import com.B1team.b01.repository.RorderRepository;
import com.B1team.b01.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service

public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private RorderRepository rorderRepository;

    @Autowired
    private PorderRepository porderRepository;

    @Autowired
    private final PinoutService pinoutService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;



    @Autowired
    public StockService(StockRepository stockRepository, PinoutService pinoutService) {
        this.stockRepository = stockRepository;
        this.pinoutService = pinoutService;
    }

    //시뮬레이션 - 재고확인 및 자동발주
    public Long stockCheck(String productId, String orderId) {
        //제품 잔여수량 확인

        Stock stock = (Stock) stockRepository.findByProductIdNotNull();

        //재고 수량
        Long stockEa = stock.getEa();

        System.out.println("stockEa" + stockEa);

//      수주 주문 수량
        Optional<Rorder> optionalOne = rorderRepository.findById(orderId);
        Rorder order = optionalOne.get();

        //주문량
        Long orderCnt = order.getCnt();

        System.out.println("orderCnt" + orderCnt);

        // 재고량 & 주문량 비교
        if (stockEa < orderCnt) {
            //재고량이 주문량보다 적으면 자동발주 계산
            System.out.println("발주계산으로 넘어감");

            Long remainingStock = orderCnt - stockEa; //주문량 - 재고량
            stock.setEa(remainingStock);
            stockRepository.save(stock);

            return remainingStock;

        } else if (stockEa >= orderCnt) {
            System.out.println("출하");

            Long remainingStock = stockEa - orderCnt; //재고량 - 주문량
            stock.setEa(remainingStock);
            stockRepository.save(stock);

            return remainingStock;

        } else {
            return null;
        }
    }

    //문자열 시퀀스 메소드
    @Transactional
    public String generateId(String head, String seqName) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT " + seqName + ".NEXTVAL FROM dual").getSingleResult();
        String id = head + sequenceValue;
        return id;
    }

    //발주 등록
    public void createPorder(PorderDto pdto) {

        // 새로운 발주 등록 insert
        Porder porder = new Porder();

        porder.setId(generateId("POR", "porder_seq"));
        porder.setOrderDate(LocalDateTime.now()); // 현재일자

        porder.setCustomerId(pdto.getCustomerId()); // 거래처 고유번호
        porder.setCustomerName(pdto.getCustomerName()); // 발주처
        porder.setArrivalDate(pdto.getArrivalDate()); // 입고일
        porder.setCnt(pdto.getCnt());
        porder.setMtrUnit(pdto.getUnit());
        porder.setAmount(pdto.getAmount());
        porder.setState(pdto.getState());
        porder.setMtrId(pdto.getMtrId());
        porder.setMtrPrice(pdto.getPrice());
        porder.setMtrName(pdto.getName());

        porderRepository.save(porder);
    }

//
//    public List<StockListDto> getProductStockList(String productName, String productId, String productSort) {
//
//        List<Object[]> results = stockRepository.getProductStockList(productName, productId, productSort);
//
//        List<StockListDto> stockListDtoList = new ArrayList<>();
//
//        for (Object[] row : results) {
//            StockListDto stockListDto = new StockListDto();
//
//            // stock
//            stockListDto.setName(row[0].toString());
//            stockListDto.setProductId(row[1].toString());
////            stockListDto.setEa(Long.valueOf((Long) row[2]));
//            stockListDto.setUnit(row[2].toString());
//            stockListDto.setPrice(Long.valueOf((Long) row[3]));
//            stockListDto.setSort(row[4].toString());
//            stockListDto.setLocation(row[5].toString());
//
//            for (int i = 0; i <= 5; i++) {
//                System.out.println(i + ":" + row[i]);
//            }
//
//            stockListDtoList.add(stockListDto);
//
//        }
//        return stockListDtoList;
//    }

    // 재고 update
    public void updateStockEa(String mtrId, double stockEa) {
        Stock stock = stockRepository.findById(mtrId).orElse(null);
        if (stock != null) {
            stock.setEa((long) stockEa);
            stockRepository.save(stock);
        }
    }




    //제품 재고 업데이트(생산작업 들어갈 때 재고에서 빠지는 작업, g단위로 빠짐)
    @Transactional
    public void deleteStockEa(String productId, long orderCnt){
        List<Long> stock = stockRepository.findByMTRStockCnt();
        Long[] oneBoxNeedProductResult = pinoutService.oneBoxNeedProduct(productId);  //출고수량
        List<Stock> stockList = new ArrayList<>();

        switch(productId) {
            case "p21":
                StockDeleteDto stockDeleteDto = new StockDeleteDto();
                stockDeleteDto.setId("st1");
                stockDeleteDto.setLocation("창고1");
                stockDeleteDto.setMtrId("MTR36");
                stockDeleteDto.setUnit("g");
                stockDeleteDto.setEa((stock.get(4) - oneBoxNeedProductResult[0]) * orderCnt);  //MTR36 양배추

                StockDeleteDto stockDeleteDto1a = new StockDeleteDto();
                stockDeleteDto1a.setId("st6");
                stockDeleteDto1a.setLocation("창고6");
                stockDeleteDto1a.setMtrId("MTR41");
                stockDeleteDto1a.setUnit("ea");
                stockDeleteDto1a.setEa((stock.get(8) - oneBoxNeedProductResult[1] * orderCnt));  //MTR41 즙파우치

                StockDeleteDto stockDeleteDto1b = new StockDeleteDto();
                stockDeleteDto1b.setId("st8");
                stockDeleteDto1b.setLocation("창고8");
                stockDeleteDto1b.setMtrId("MTR43");
                stockDeleteDto1b.setUnit("ea");
                stockDeleteDto1b.setEa((stock.get(0) - oneBoxNeedProductResult[2] * orderCnt));  //MTR43 박스


                Stock stockEntity = stockDeleteDto.toEntity();
                stockList.add(stockEntity);

                Stock stockEntity1a = stockDeleteDto1a.toEntity();
                stockList.add(stockEntity1a);

                Stock stockEntity1b = stockDeleteDto1b.toEntity();
                stockList.add(stockEntity1b);
                stockRepository.saveAll(stockList);


                break;

            case "p22":
                StockDeleteDto stockDeleteDto2 = new StockDeleteDto();
                stockDeleteDto2.setId("st2");
                stockDeleteDto2.setLocation("창고2");
                stockDeleteDto2.setMtrId("MTR37");
                stockDeleteDto2.setUnit("g");
                stockDeleteDto2.setEa((stock.get(4) - oneBoxNeedProductResult[0] * orderCnt));   //MTR37 흑마늘

                StockDeleteDto stockDeleteDto2a = new StockDeleteDto();
                stockDeleteDto2a.setId("st6");
                stockDeleteDto2a.setLocation("창고6");
                stockDeleteDto2a.setMtrId("MTR41");
                stockDeleteDto2a.setUnit("ea");
                stockDeleteDto2a.setEa((stock.get(8) - oneBoxNeedProductResult[1] * orderCnt));  //MTR41 즙파우치

                StockDeleteDto stockDeleteDto2b = new StockDeleteDto();
                stockDeleteDto2b.setId("st8");
                stockDeleteDto2b.setLocation("창고8");
                stockDeleteDto2b.setMtrId("MTR43");
                stockDeleteDto2b.setUnit("ea");
                stockDeleteDto2b.setEa((stock.get(0) - oneBoxNeedProductResult[2] * orderCnt));   //MTR43 박스

                Stock stockEntity2 = stockDeleteDto2.toEntity();
                stockList.add(stockEntity2);

                Stock stockEntity2a = stockDeleteDto2a.toEntity();
                stockList.add(stockEntity2a);

                Stock stockEntity2b = stockDeleteDto2b.toEntity();
                stockList.add(stockEntity2b);
                stockRepository.saveAll(stockList);
                break;

            case "p23":
                StockDeleteDto stockDeleteDto4 = new StockDeleteDto();
                stockDeleteDto4.setId("st3");
                stockDeleteDto4.setLocation("창고3");
                stockDeleteDto4.setMtrId("MTR38");
                stockDeleteDto4.setUnit("g");
                stockDeleteDto4.setEa((stock.get(5) - oneBoxNeedProductResult[0] * orderCnt));   //MTR38 석류농축액

                StockDeleteDto stockDeleteDto4a = new StockDeleteDto();
                stockDeleteDto4a.setId("st5");
                stockDeleteDto4a.setLocation("창고5");
                stockDeleteDto4a.setMtrId("MTR40");
                stockDeleteDto4a.setUnit("g");
                stockDeleteDto4a.setEa((stock.get(7) - oneBoxNeedProductResult[1] * orderCnt));   //MTR40 콜라겐

                StockDeleteDto stockDeleteDto4b = new StockDeleteDto();
                stockDeleteDto4b.setId("st7");
                stockDeleteDto4b.setLocation("창고7");
                stockDeleteDto4b.setMtrId("MTR42");
                stockDeleteDto4b.setUnit("ea");
                stockDeleteDto4b.setEa((stock.get(9) - oneBoxNeedProductResult[2] * orderCnt));  //MTR42 스틱파우치

                StockDeleteDto stockDeleteDto4c = new StockDeleteDto();
                stockDeleteDto4c.setId("st8");
                stockDeleteDto4c.setLocation("창고8");
                stockDeleteDto4c.setMtrId("MTR43");
                stockDeleteDto4c.setUnit("ea");
                stockDeleteDto4c.setEa((stock.get(0) - oneBoxNeedProductResult[3] * orderCnt));   //MTR43 박스


                Stock stockEntity4 = stockDeleteDto4.toEntity();
                stockList.add(stockEntity4);

                Stock stockEntity4a = stockDeleteDto4a.toEntity();
                stockList.add(stockEntity4a);

                Stock stockEntity4b = stockDeleteDto4b.toEntity();
                stockList.add(stockEntity4b);

                Stock stockEntity4c = stockDeleteDto4c.toEntity();
                stockList.add(stockEntity4c);
                stockRepository.saveAll(stockList);
                break;

            case "p24":
                StockDeleteDto stockDeleteDto3 = new StockDeleteDto();
                stockDeleteDto3.setId("st4");
                stockDeleteDto3.setLocation("창고4");
                stockDeleteDto3.setMtrId("MTR39");
                stockDeleteDto3.setUnit("g");
                stockDeleteDto3.setEa((stock.get(3) - oneBoxNeedProductResult[0] * orderCnt));  //MTR39 매실농축액

                StockDeleteDto stockDeleteDto3a = new StockDeleteDto();
                stockDeleteDto3a.setId("st5");
                stockDeleteDto3a.setLocation("창고5");
                stockDeleteDto3a.setMtrId("MTR40");
                stockDeleteDto3a.setUnit("g");
                stockDeleteDto3a.setEa((stock.get(4) - oneBoxNeedProductResult[1] * orderCnt));  //MTR40 콜라겐

                StockDeleteDto stockDeleteDto3b = new StockDeleteDto();
                stockDeleteDto3b.setId("st7");
                stockDeleteDto3b.setLocation("창고7");
                stockDeleteDto3b.setMtrId("MTR42");
                stockDeleteDto3b.setUnit("ea");
                stockDeleteDto3b.setEa((stock.get(9) - oneBoxNeedProductResult[2] * orderCnt));   //MTR42 스틱파우치

                StockDeleteDto stockDeleteDto3c = new StockDeleteDto();
                stockDeleteDto3c.setId("st8");
                stockDeleteDto3c.setLocation("창고8");
                stockDeleteDto3c.setMtrId("MTR43");
                stockDeleteDto3c.setUnit("ea");
                stockDeleteDto3c.setEa((stock.get(0) - oneBoxNeedProductResult[3] * orderCnt));   //MTR43 박스

                Stock stockEntity3 = stockDeleteDto3.toEntity();
                stockList.add(stockEntity3);

                Stock stockEntity3a = stockDeleteDto3a.toEntity();
                stockList.add(stockEntity3a);

                Stock stockEntity3b = stockDeleteDto3b.toEntity();
                stockList.add(stockEntity3b);

                Stock stockEntity3c = stockDeleteDto3c.toEntity();
                stockList.add(stockEntity3c);
                stockRepository.saveAll(stockList);

                break;

        }
    }



}