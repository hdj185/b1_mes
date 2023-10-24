package com.B1team.b01.repository;

import com.B1team.b01.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> , JpaSpecificationExecutor<Customer> {
    List<Customer> findBySort(String customerSort);

    @Query(value = "SELECT customer_seq.nextval FROM dual", nativeQuery = true)
    Long getNextCustomerSeq();

}
