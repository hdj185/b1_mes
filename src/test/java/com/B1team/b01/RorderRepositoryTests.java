package com.B1team.b01;

import com.B1team.b01.dto.RorderDto;
import com.B1team.b01.dto.RorderFormDto;
import com.B1team.b01.entity.Rorder;
import com.B1team.b01.repository.RorderRepository;
import com.B1team.b01.service.RorderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class RorderRepositoryTests {
    @Autowired private RorderRepository rorderRepository;
    @Autowired private RorderService rorderService;
    @Test
    void 수주검색테스트() {
        //검색 조건
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        String orderId = null;
        String customerName = null;
        String productName = null;
        LocalDateTime startDeadline = LocalDateTime.of(2023, 05, 25, 0, 0, 0, 0);
        LocalDateTime endDeadLine = LocalDateTime.of(2023, 05, 25, 23, 59, 59, 0);

        //메소드 변경으로 작동 안됨
//        List<Rorder> list = rorderRepository.findRordersByConditions(startDate, endDate, orderId, customerName, productName, startDeadline, endDeadLine);
//        for(int i = 0; i < list.size(); i++)
//            System.out.println(list.get(i));
    }

    @Test
    void 수주검색서비스테스트() {
        //메소드 매개변수 수정으로 작동 불가
        /*
        //검색 조건
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        String orderId = null;
        String customerName = null;
        String productName = null;
        LocalDateTime startDeadline = LocalDateTime.of(2023, 05, 25, 0, 0, 0, 0);
        LocalDateTime endDeadLine = LocalDateTime.of(2023, 05, 25, 23, 59, 59, 0);

        List<RorderDto> list = rorderService.searchRorder(startDate, endDate, orderId, customerName, productName, startDeadline, endDeadLine);
        for(int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

         */
    }

    @Test
    void 수주등록테스트(@Autowired EntityManager entityManager) {
        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT " + "order_seq" + ".NEXTVAL FROM dual").getSingleResult();
        String id = "ROD" + sequenceValue;
        RorderFormDto dto = new RorderFormDto().builder()
                .id(id)
                .customerId("code1")
                .customerName("코드하우스")
                .productId("p21")
                .productName("양배추즙")
                .cnt(50L)
                .price(500000L)
                .state("미확정")
                .deadline(LocalDateTime.now().plusDays(2))
                .build();
        Rorder entity = dto.toEntity();
        System.out.println(rorderRepository.save(entity));
    }

    @Transactional
    @Test
    void 수주확정테스트() {
        System.out.println("테스트----------------------");
        System.out.println(rorderRepository.updateState("ROD28"));
        System.out.println("테스트----------------------");
    }
}
