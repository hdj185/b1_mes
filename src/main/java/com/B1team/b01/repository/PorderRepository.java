package com.B1team.b01.repository;

import com.B1team.b01.entity.Porder;
import com.B1team.b01.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PorderRepository extends JpaRepository<Porder,String> {
    //발주 현황 검색 및 리스트
    @Query("SELECT p FROM Porder p " +
            "WHERE (:startDate IS NULL OR p.orderDate >= :startDate) " +
            "AND (:endDate IS NULL OR p.orderDate <= : endDate) " +
            "AND (:mtrName IS NULL OR p.mtrName = :mtrName) " +
            "AND (:customerName IS NULL OR p.customerName = :customerName) " +
            "AND (:state IS NULL OR (:state = '예정' AND p.arrivalDate > :currentTime) OR (:state = '완료' AND p.arrivalDate <= :currentTime)) " +
            "AND (:startArrivalDate IS NULL OR p.arrivalDate >= : startArrivalDate) " +
            "AND (:endArrivalDate IS NULL OR p.arrivalDate <= : endArrivalDate) " +
            "ORDER BY p.id DESC")
    List<Porder> findPordersByConditons(@Param("startDate")LocalDateTime startDate,
                                        @Param("endDate")LocalDateTime endDate,
                                        @Param("mtrName")String mtrName,
                                        @Param("customerName")String customerName,
                                        @Param("state")String state,
                                        @Param("currentTime")LocalDateTime currentTime,
                                        @Param("startArrivalDate")LocalDateTime startArrivalDate,
                                        @Param("endArrivalDate")LocalDateTime endArrivalDate);
}
