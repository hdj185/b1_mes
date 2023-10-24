package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINPROD")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Finprod {
    @Id
    @Column(name = "finprod_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finprod_seq_generator")
    @SequenceGenerator(name = "finprod_seq_generator", sequenceName = "FINPROD_SEQ", initialValue = 10001, allocationSize = 1)
    private String id;        //완제품 고유번호

    @Column(name="product_id", nullable = false, columnDefinition = "varchar2(50)")
    private String productId;    //제품 고유번호

    @Column(name="order_id", nullable = false, columnDefinition = "varchar2(50)")
    private String orderId;    //수주 고유번호

    @Column(name="finprod_ea", nullable = false, columnDefinition = "number(10)")
    private Long ea;    //완제품 수량

    @Column(name="finprod_deadline", nullable = false, columnDefinition = "date")
    private LocalDateTime deadline;    //납품예정일

    @Column(name="finprod_product", nullable = false, columnDefinition = "varchar2(50)")
    private String product;    //품목명
}
