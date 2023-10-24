package com.B1team.b01.repository;

import com.B1team.b01.entity.LOT;
import com.B1team.b01.entity.Wperform;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class LotSpecifications {
    public static Specification<LOT> searchId(String id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<LOT> searchProcessId(String processId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("processId"), processId);
    }

    public static Specification<LOT> searchProductId(String productId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productId"), productId);
    }

    public static Specification<LOT> searchDate(LocalDateTime min, LocalDateTime max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("date"),min,max);
    }
}
