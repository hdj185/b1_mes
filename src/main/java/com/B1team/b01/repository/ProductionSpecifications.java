package com.B1team.b01.repository;

import com.B1team.b01.entity.Wplan;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Date;

public class ProductionSpecifications {
    public static Specification<Wplan> searchId(String id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productId"),id);
    }

    public static Specification<Wplan> searchOrderId(String orderId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("orderId"),orderId);
    }

    public static Specification<Wplan> searchState(String state){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"),state);
    }

    public static Specification<Wplan> searchDate(LocalDateTime min,LocalDateTime max){
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("startDate"),min,max);
    }
}
