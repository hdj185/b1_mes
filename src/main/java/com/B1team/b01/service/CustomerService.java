package com.B1team.b01.service;

import com.B1team.b01.entity.Customer;
import com.B1team.b01.entity.Wplan;
import com.B1team.b01.repository.CustomerRepository;
import com.B1team.b01.repository.CustomerSpecifications;
import com.B1team.b01.repository.ProductionSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;

    public List<Customer> getPorderList(String sort){
        return customerRepository.findBySort(sort);
    }

    public List<Customer> search(String name,String id,String businessNumber,String sort){
        Specification<Customer> specification = Specification.where(null);

        if(name != null){
            specification = specification.and(CustomerSpecifications.searchName(name));
        }
        if(id != null){
            specification = specification.and(CustomerSpecifications.searchId(id));
        }
        if(businessNumber != null){
            specification = specification.and(CustomerSpecifications.searchBNumber(businessNumber));
        }
        if(sort != null){
            specification = specification.and(CustomerSpecifications.searchSort(sort));
        }

        return customerRepository.findAll(specification);
    }

    public void insertCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public Long getNextCustomerSeq(){
        return customerRepository.getNextCustomerSeq();
    }
}
