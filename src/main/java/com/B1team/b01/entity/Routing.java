package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="ROUTING")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Routing {
    @Id
    @Column(name = "routing_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routing_seq_generator")
    @SequenceGenerator(name = "routing_seq_generator", sequenceName = "ROUTING_SEQ", initialValue = 10001, allocationSize = 1)
    private String id;        //라우팅 고유번호

    @Column(name = "product_id", nullable = false, columnDefinition = "varchar2(50)")
    private String productId;    //제품 고유번호

    @Column(name="process_id", nullable = false, columnDefinition = "varchar2(50)")
    private String processId;    //공정 고유번호(공정코드)

    @Column(name="routing_order", nullable = false, columnDefinition = "number(10)")
    private Long order;    //순서

}
