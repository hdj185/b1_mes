package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="SINVEN")
@Getter
@Setter
@NoArgsConstructor
public class Sinven {
    @Id
    @Column(name = "sinven_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sinven_seq_generator")
    @SequenceGenerator(name = "sinven_seq_generator", sequenceName = "sinven_seq", initialValue = 10001, allocationSize = 1)
    private String id;

    @Column(name="product_id", nullable = false, columnDefinition = "varchar2(50)")
    private String productId;

    @Column(name="sinven_ea", nullable = false, columnDefinition = "number(10)")
    private Long ea;

    @Column(name="process_id", nullable = false, columnDefinition = "varchar2(50)")
    private String processId;

    @Column(name="stock_unit", nullable = false, columnDefinition = "varchar2(10)")
    private String stockUnit;

}
