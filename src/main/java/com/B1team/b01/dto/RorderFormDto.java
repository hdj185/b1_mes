package com.B1team.b01.dto;

import com.B1team.b01.entity.Rorder;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RorderFormDto {
    public String id;
    public String customerId;
    public String customerName;
    public String productId;
    public String productName;
    public Long cnt;
    public Long price;
    public String state;
    public LocalDateTime date;
    public LocalDateTime changedate;
    public LocalDateTime deadline;

    private static ModelMapper modelMapper = new ModelMapper(); //엔티티랑 dto의 필드명이 같은 것끼리 매핑

//    @Autowired private EntityManager entityManager;

    public RorderFormDto(String rorderId,
                         String orderDate,
                         String customerId,
                         String customerName,
                         String productId,
                         String productName,
                         String orderCnt,
                         String orderPrice,
                         String deliveryDate) {
        this(orderDate, customerId, customerName, productId, productName, orderCnt, orderPrice, deliveryDate);
        setId(rorderId);
    }

    public RorderFormDto(String orderDate,
                         String customerId,
                         String customerName,
                         String productId,
                         String productName,
                         String orderCnt,
                         String orderPrice,
                         String deliveryDate) {
        DateTimeFormatter orderFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);
        DateTimeFormatter deliveryFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd a HH:mm");
        setDate(LocalDateTime.parse(orderDate, orderFormatter));
        setChangedate(getDate());
        setDeadline(LocalDateTime.parse(deliveryDate, deliveryFormatter));
        setCustomerId(customerId);
        setCustomerName(customerName);
        setProductId(productId);
        setProductName(productName);
        setCnt(Long.parseLong(orderCnt));
        setState("미확정");
        setPrice(Long.parseLong(orderPrice));   //TODO:나중에 price 뷰에서 계산해서 받기
    }

    //RorderFormDto를 Order 엔티티로 변환해주는 메소드
    public Rorder toEntity() {
//        this.setId(generateId("ROD", "order_seq"));
        return modelMapper.map(this, Rorder.class);
    }

    //문자열 시퀀스 메소드
//    @Transactional
//    public String generateId(String head, String seqName) {
//        BigDecimal sequenceValue = (BigDecimal) entityManager.createNativeQuery("SELECT " + seqName + ".NEXTVAL FROM dual").getSingleResult();
//        String id = head + sequenceValue;
//        return id;
//    }
}
