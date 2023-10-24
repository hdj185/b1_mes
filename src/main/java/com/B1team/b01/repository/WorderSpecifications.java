package com.B1team.b01.repository;

import com.B1team.b01.entity.Worder;
import com.B1team.b01.entity.Wplan;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class WorderSpecifications {
    public static Specification<Worder> searchId(String id){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productId"),id));
    }

    public static Specification<Worder> searchPId(String Pid){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("processId"),Pid));
    }

    public static Specification<Worder> searchDate(LocalDateTime min, LocalDateTime max){
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("finishDate"),min,max);
    }
}
