package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOT")
@Getter
@Setter
@NoArgsConstructor
public class LOT {
    @Id
    @Column(name = "lot_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lot_seq_generator")
    @SequenceGenerator(name = "lot_seq_generator", sequenceName = "LOT_SEQ", initialValue = 10001, allocationSize = 1)
    private String id;    //로트 고유번호

    @Column(name="order_id", nullable = false, columnDefinition = "varchar2(50)")
    private String orderId;    //수주 고유번호

    @Column(name="lot_code", nullable = false, columnDefinition = "varchar2(20)")
    private String code;    //로트 코드

    @Column(name="lot_process_id", nullable = false, columnDefinition = "varchar2(5)")
    private String processId; //공정 코드

    @Column(name="lot_product_id", nullable = false, columnDefinition = "varchar2(50)")
    private String productId; //제품 고유번호

    @Column(name="worder_id", nullable = false, columnDefinition = "varchar2(50)")
    private String worderId;  //작업지시 고유번호

    @Column(name="lot_date", nullable = false, columnDefinition = "date")
    private LocalDateTime date;  //로트 완료일

    @Column(name="finprod_id", nullable = false, columnDefinition = "varchar2(50)")
    private String finprodId;  //완제품 고유번호

}
