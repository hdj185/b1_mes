package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jdbc.repository.query.Query;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="PORDER")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Porder {
    @Id
    @Column(name = "porder_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "porder_seq_generator")
    @SequenceGenerator(name = "porder_seq_generator", sequenceName = "porder_seq", initialValue = 10001, allocationSize = 1)
    //발주 고유번호
    private String id;

    //발주일
    @Column(name="porder_date", nullable = false, columnDefinition = "date")
    private LocalDateTime orderDate;

    //거래처 고유번호
    @Column(name="customer_id", nullable = false, columnDefinition = "varchar2(50)")
    private String customerId;

    //발주처명
    @Column(name="customer_name", nullable = false, columnDefinition = "varchar2(50)")
    private String customerName;

    //입고일
    @Column(name="arrival_date", nullable = false, columnDefinition = "date")
    private LocalDateTime arrivalDate;

    //품목 수량
    @Column(name="porder_cnt", nullable = false, columnDefinition = "number(10)")
    private Long cnt;

    //품목 단위
    @Column(name="mtr_unit", nullable = false, columnDefinition = "varchar2(3)")
    private String mtrUnit;

    //발주 금액
    @Column(name="porder_amount", nullable = false, columnDefinition = "number(10)")
    private Long amount;

    //입고 여부
    @Column(name="porder_state", nullable = false, columnDefinition = "varchar2(12)")
    private String state;

    //원자재 고유번호
    @Column(name="mtr_id", nullable = false, columnDefinition = "varchar2(50)")
    private String mtrId;

    //구매 단가
    @Column(name="mtr_price", nullable = false, columnDefinition = "number(10)")
    private Long mtrPrice;

    //원자재명
    @Column(name="mtr_name", nullable = false, columnDefinition = "varchar2(50)")
    private String mtrName;

    }
