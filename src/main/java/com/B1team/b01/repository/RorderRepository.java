package com.B1team.b01.repository;

import com.B1team.b01.entity.Rorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RorderRepository extends JpaRepository<Rorder, String> {
    @Query("SELECT r FROM Rorder r " +
            "WHERE (:startDate IS NULL OR r.date >= :startDate) " +
            "AND (:endDate IS NULL OR r.date <= :endDate) " +
            "AND (:orderId IS NULL OR r.id = :orderId) " +
            "AND (:state IS NULL OR " +
            "(:state = '진행전' AND r.state = '미확정' AND :now IS NULL) OR " +
            "(:state = '진행중' And r.state = '확정' AND r.deadline > :now) OR " +
            "(:state = '완료' AND r.state = '확정' AND r.deadline <= :now)) " +
            "AND (:customerName IS NULL OR r.customerName = :customerName) " +
            "AND (:productName IS NULL OR r.productName = :productName) " +
            "AND (:startDeadline IS NULL OR r.deadline >= :startDeadline) " +
            "AND (:endDeadLine IS NULL OR r.deadline <= :endDeadLine) " +
            "ORDER BY r.id DESC")
    List<Rorder> findRordersByConditions(@Param("startDate") LocalDateTime startDate,
                                                                         @Param("endDate")LocalDateTime endDate,
                                                                         @Param("orderId")String orderId,
                                                                         @Param("state")String state,
                                                                         @Param("now")LocalDateTime now,
                                                                         @Param("customerName")String customerName,
                                                                         @Param("productName")String productName,
                                                                         @Param("startDeadline")LocalDateTime startDeadline,
                                                                         @Param("endDeadLine")LocalDateTime endDeadLine);



    Optional<Rorder> findById(String Id);

    @Query("SELECT ro.cnt FROM Rorder ro WHERE ro.id = :orderId")
    Long findByOrderCnt(String orderId);


    //수주 확정
    @Modifying
    @Query("UPDATE Rorder r set r.state = '확정' WHERE r.id = :id")
    int updateState(@Param("id") String id);



}
