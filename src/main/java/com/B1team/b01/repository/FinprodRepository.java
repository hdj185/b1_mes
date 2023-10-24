package com.B1team.b01.repository;

import com.B1team.b01.dto.ChartSumDTO;
import com.B1team.b01.dto.ShipmentDto;
import com.B1team.b01.entity.Finprod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FinprodRepository extends JpaRepository<Finprod, String> {

    //출하관리 페이지 - 완제품에 거래처 추가하여 리스트 얻기
    @Query("SELECT NEW com.B1team.b01.dto.ShipmentDto(f.id, f.deadline, f.product, f.ea, f.orderId, r.customerName) " +
            "FROM Finprod f JOIN Rorder r ON f.orderId = r.id " +
            "WHERE (:customerName IS NULL OR r.customerName = :customerName) " +
            "AND (:productName IS NULL OR f.product = :productName) " +
            "AND (:orderId IS NULL OR f.orderId = :orderId) " +
            "AND (:startDate IS NULL OR f.deadline >= :startDate) " +
            "AND (:endDate IS NULL OR f.deadline <= :endDate) " +
            "AND (:currentTime >= f.deadline) " +
            "ORDER BY f.deadline DESC")
    List<ShipmentDto> findShipmentsByConditions(@Param("customerName") String customerName,
                                                @Param("productName") String productName,
                                                @Param("orderId") String orderId,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate,
                                                @Param("currentTime") LocalDateTime currentTime);


    //올해 월별 생산량 합
    @Query("SELECT NEW com.B1team.b01.dto.ChartSumDTO(MONTH(f.deadline), SUM(f.ea)) " +
            "FROM Finprod f WHERE YEAR(f.deadline) = YEAR(CURRENT_TIMESTAMP) AND f.deadline <= CURRENT_TIMESTAMP GROUP BY MONTH(f.deadline) " +
            "ORDER BY MONTH(f.deadline)")
    List<ChartSumDTO> getMonthlyEaSum();

    //이번달 일별 생산량 합
    @Query("SELECT NEW com.B1team.b01.dto.ChartSumDTO(DAY(f.deadline), SUM(f.ea)) " +
            "FROM Finprod f WHERE MONTH(f.deadline) = MONTH(CURRENT_TIMESTAMP) AND f.deadline <= CURRENT_TIMESTAMP GROUP BY DAY(f.deadline) " +
            "ORDER BY DAY(f.deadline)")
    List<ChartSumDTO> getDaillyEaSum();
}
