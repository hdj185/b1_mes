package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="WPERFORM")
@Getter
@Setter
@NoArgsConstructor
public class Wperform {
    @Id
    @Column(name = "wperform_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wperform_seq_generator")
    @SequenceGenerator(name = "wperform_seq_generator", sequenceName = "wperform_seq", initialValue = 10001, allocationSize = 1)
    private String id;

    @Column(name="wplan_id", nullable = false, columnDefinition = "varchar2(50)")
    private String wplanId;

    @Column(name="order_id", nullable = false, columnDefinition = "varchar2(50)")
    private String orderId;

    @Column(name="wperform_start_date", nullable = false, columnDefinition = "date")
    private LocalDateTime startDate;

    @Column(name="wperform_cnt", nullable = false, columnDefinition = "number(10)")
    private Long cnt;

    @Column(name="wperform_end_date", nullable = false, columnDefinition = "date")
    private LocalDateTime endDate;

    @Column(name="product_id", nullable = false, columnDefinition = "varchar2(50)")
    private String productId; //제품 고유번호
}
