package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="WORDER")
@Getter
@Setter
@NoArgsConstructor
public class Worder {
    @Id
    @Column(name = "worder_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "worder_seq_generator")
    @SequenceGenerator(name = "worder_seq_generator", sequenceName = "WORDER_SEQ", initialValue = 10001, allocationSize = 1)
    private String id;        //작업지시 고유번호

    @Column(name = "process_id", nullable = false, columnDefinition = "varchar2(50)")
    private String processId;    //공정 고유번호

    @Column(name = "wplan_id", nullable = false, columnDefinition = "varchar2(50)")
    private String wplanId;    //작업계획 고유번호

    @Column(name = "facility_id", nullable = false, columnDefinition = "varchar2(50)")
    private String facilityId;    //설비정보 고유번호

    @Column(name = "worder_start_date", nullable = false, columnDefinition = "date")
    private LocalDateTime startDate;   //작업 시작 일자

    @Column(name = "worder_finish_date", nullable = false, columnDefinition = "date")
    private LocalDateTime finishDate;   //작업 완료 일자

    @Column(name="product_id", columnDefinition = "varchar2(50)")
    private String productId; //제품 고유번호

    @Column(name="order_id", columnDefinition = "varchar2(50)")
    private String orderId; //수주 고유번호
}
