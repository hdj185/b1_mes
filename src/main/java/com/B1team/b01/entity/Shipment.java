package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
///////////////////////////Shipment DB 관련(엔티티, 레포지토리 등)은 필요 없음!!!!
@Entity
@Table(name="SHIPMENT")
@Getter
@Setter
@NoArgsConstructor
public class Shipment {
    @Id
    @Column(name = "shipment_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "porder_seq_generator")
    @SequenceGenerator(name = "porder_seq_generator", sequenceName = "porder_seq", initialValue = 10001, allocationSize = 1)
    //출하 고유번호
    private String id;

    //완제품 고유번호
    @Column(name="worder_id", nullable = false, columnDefinition = "varchar2(50)")
    private String worderId;

    //제품 고유번호
    @Column(name="product_id", nullable = false, columnDefinition = "varchar2(50)")
    private String customerId;

    //수량
    @Column(name="shipment_cnt", nullable = false, columnDefinition = "number(10)")
    private Long cnt;

    //품목명
    @Column(name="shipment_product_name", nullable = false, columnDefinition = "varchar2(50)")
    private String productName;

    //출하일
    @Column(name="shipment_day", nullable = false, columnDefinition = "number(10)")
    private Long day;

    }
