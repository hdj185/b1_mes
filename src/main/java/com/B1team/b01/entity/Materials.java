package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="MATERIALS")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Materials {
    @Id
    @Column(name = "mtr_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mtr_seq_generator")
    @SequenceGenerator(name = "mtr_seq_generator", sequenceName = "MTR_SEQ", initialValue = 10001, allocationSize = 1)
    private String id;        //원자재 고유번호

    @Column(name="mtr_name", nullable = false, columnDefinition = "varchar2(50)")
    private String name;    //자재명

    @Column(name="mtr_location", nullable = false, columnDefinition = "varchar2(20)")
    private String location;    //창고위치

    @Column(name="mtr_price", nullable = false, columnDefinition = "number(10)")
    private Long price;    //구매단가

    @Column(name="mtr_unit", nullable = false, columnDefinition = "varchar2(10)")
    private String unit;    //단위

    @Column(name="mtr_min_cnt", nullable = false, columnDefinition = "number(10)")
    private Long minCnt;    //발주 최소 수량

    @Column(name="mtr_max_cnt", nullable = false, columnDefinition = "number(10)")
    private Long maxCnt;    //발주 최대 수량

    @Column(name="mtr_leadtime", nullable = false, columnDefinition = "number(10)")
    private Long leadtime;    //발주 리드타임(발주하고 입고까지 걸리는 시간)

    @Column(name="mtr_cutoff_time", nullable = false, columnDefinition = "number(10)")
    private Long cutoffTime;    //발주 주문 처리 기준 시간(~시보다 뒤에 주문하면 다음날로 주문 처리)

}
