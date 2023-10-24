package com.B1team.b01.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="CUSTOMER")
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @Column(name = "customer_id", nullable = false, columnDefinition = "varchar2(50)")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq_generator")
    @SequenceGenerator(name = "customer_seq_generator", sequenceName = "customer_seq", initialValue = 10001, allocationSize = 1)
    private String id; // 업체 코드

    @Column(name="customer_sort", nullable = false, columnDefinition = "varchar2(9)")
    private String sort; //업체 분류

    @Column(name="customer_name", nullable = false, columnDefinition = "varchar2(50)")
    private String name; //업체명

    @Column(name="customer_address", nullable = false, columnDefinition = "varchar2(100)")
    private String address; //업체 주소

    @Column(name="customer_tel", nullable = false, columnDefinition = "varchar2(15)")
    private String tel; //연락처

    @Column(name="customer_business_number", nullable = false, columnDefinition = "varchar2(20)")
    private String businessNumber; //사업자번호

    @Column(name="customer_fax", nullable = false, columnDefinition = "varchar2(20)")
    private String fax; //팩스번호
}
