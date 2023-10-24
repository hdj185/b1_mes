package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="RORDER")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Rorder {
    @Id
    @Column(name = "order_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_generator")
    @SequenceGenerator(name = "order_seq_generator", sequenceName = "order_seq", initialValue = 10001, allocationSize = 1)
    private String id;

//    @CreatedDate
    @Column(name="order_date", nullable = false, columnDefinition = "date")
    private LocalDateTime date;

    @Column(name="customer_id", nullable = false, columnDefinition = "varchar2(50)")
    private String customerId;

    @Column(name="customer_name", nullable = false, columnDefinition = "varchar2(50)")
    private String customerName;

    @Column(name="order_deadline", nullable = false, columnDefinition = "date")
    private LocalDateTime deadline;

    @Column(name="order_price", nullable = false, columnDefinition = "number(10)")
    private Long price;

    @LastModifiedDate
    @Column(name="order_change_date", nullable = false, columnDefinition = "date")
    private LocalDateTime changedate;

    @Column(name="order_state", nullable = false, columnDefinition = "varchar2(12)")
    private String state;

    @Column(name="product_id", nullable = false, columnDefinition = "varchar2(50)")
    private String productId;

    @Column(name="order_cnt", nullable = false, columnDefinition = "number(10)")
    private Long cnt;

    @Column(name="product_name", nullable = false, columnDefinition = "varchar2(50)")
    private String productName;
}
