package com.B1team.b01.repository;

import com.B1team.b01.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

///////////////////////////Shipment 관련(엔티티, 레포지토리 등)은 필요 없음!!!!
public interface ShipmentRepository extends JpaRepository<Shipment, String> {

    //출하 관리 페이지 리스트 출력
    @Query("SELECT s FROM Shipment s " +
            "WHERE (:startDate IS NULL OR s.day >= : startDate)" +
            "AND (:endDate IS NULL OR s.day <= :endDate) ")
    List<Shipment> findShipmentsByConditions(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate")LocalDateTime endDate);
}
