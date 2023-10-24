package com.B1team.b01.repository;

import com.B1team.b01.entity.Rorder;
import com.B1team.b01.entity.Wperform;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WperformSpecifications {
    public static Specification<Wperform> searchProductId(String productId){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productId"),productId));
    }

    public static Specification<Wperform> searchOrderId(String orderId){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("orderId"),orderId));
    }

    public static Specification<Wperform> searchDate(LocalDateTime min, LocalDateTime max){
        return (Root<Wperform> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            // 시작일과 종료일 사이에 포함되는지 확인하는 조건
            Predicate startDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), min);
            Predicate endDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), max);

            // 기간 안에 포함되는지 확인하는 조건
            Predicate condition = criteriaBuilder.and(startDatePredicate, endDatePredicate);

            return condition;
        };
    }


}
