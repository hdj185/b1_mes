package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="PRODUCT")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "product_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_generator")
    @SequenceGenerator(name = "product_seq_generator", sequenceName = "PRODUCT_SEQ", initialValue = 10001, allocationSize = 1)
    private String id;        //제품 고유번호

    @Column(name="product_name", nullable = false, columnDefinition = "varchar2(50)")
    private String name;    //제품명

    @Column(name="product_price", nullable = false, columnDefinition = "number(10)")
    private Long price;    //가격

    @Column(name="product_sort", nullable = false, columnDefinition = "varchar2(15)")
    private String sort;    //분류

    @Column(name="location", nullable = false, columnDefinition = "varchar2(20)")
    private String location;    //창고위치

}
