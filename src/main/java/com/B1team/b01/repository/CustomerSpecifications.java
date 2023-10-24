package com.B1team.b01.repository;

import com.B1team.b01.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecifications {
    public static Specification<Customer> searchName(String name){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),"%"+name+"%");
    }

    public static Specification<Customer> searchId(String id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"),"%"+id+"%");
    }

    public static Specification<Customer> searchBNumber(String businessNumber){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("businessNumber"),"%"+businessNumber+"%");
    }

    public static Specification<Customer> searchSort(String sort){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sort"),sort);
    }
}
