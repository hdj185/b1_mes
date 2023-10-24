package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="STOCK")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Stock {
    @Id
    @Column(name = "stock_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_seq_generator")
    @SequenceGenerator(name = "stock_seq_generator", sequenceName = "stock_seq", initialValue = 10001, allocationSize = 1)
    //재고 고유번호
    private String id;

    //제품 고유번호
    @Column(name="product_id", columnDefinition = "varchar2(50)")
    private String productId;

    //원자재 고유번호
    @Column(name="mtr_id", columnDefinition = "varchar2(50)")
    private String mtrId;

    //창고위치
    @Column(name="location", nullable = false, columnDefinition = "varchar2(20)")
    private String location;

    //잔여수량
    @Column(name="stock_ea", nullable = false, columnDefinition = "number(10)")
    private Long ea;

    //단위
    @Column(name="stock_unit", nullable = false, columnDefinition = "varchar2(10)")
    private String unit;
}
