package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="PINOUT")
@Getter
@Setter
@NoArgsConstructor
public class Pinout {
    @Id
    @Column(name = "pinout_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pinout_seq_generator")
    @SequenceGenerator(name = "pinout_seq_generator", sequenceName = "pinout_seq", initialValue = 10001, allocationSize = 1)
    private String id;

    @Column(name="mtr_id", nullable = false, columnDefinition = "varchar2(50)")
    private String mtrId;

    @Column(name="pinout_product_cnt", nullable = false, columnDefinition = "number(10)")
    private Long productCnt;

    @Column(name="pinout_product_date", nullable = false, columnDefinition = "date")
    private LocalDateTime productDate;

    @Column(name="pinout_sort", nullable = false, columnDefinition = "varchar2(10)")
    private String sort;

}
