package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="FINFO")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Finfo {
    @Id
    @Column(name = "facility_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facility_seq_generator")
    @SequenceGenerator(name = "facility_seq_generator", sequenceName = "FACILITY_SEQ", initialValue = 10001, allocationSize = 1)
    private String id;        //설비 고유번호

    @Column(name="facility_name", nullable = false, columnDefinition = "varchar2(50)")
    private String name;    //자재명

    @Column(name="facility_capa", nullable = false, columnDefinition = "varchar2(30)")
    private String capa;    //생산가능량

    @Column(name="facility_company", nullable = false, columnDefinition = "varchar2(20)")
    private String company;    //제조사

    @Column(name="facility_model", nullable = false, columnDefinition = "varchar2(50)")
    private String model;    //모델명

    @Column(name="facility_location", nullable = false, columnDefinition = "varchar2(20)")
    private String location;    //설비위치

    @Column(name="facility_inspection_date", nullable = false, columnDefinition = "DATE")
    private LocalDateTime inspectionDate;    //최근 점검 일자

    @Column(name="facility_leadtime", nullable = false, columnDefinition = "NUMBER(10)")
    private Long leadtime;    //생산 준비 시간
}
