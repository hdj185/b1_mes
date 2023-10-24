package com.B1team.b01.repository;

import com.B1team.b01.dto.PinoutOutputDto;
import com.B1team.b01.entity.Pinout;
import com.B1team.b01.entity.Stock;
import com.B1team.b01.entity.Worder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PinoutRepository extends JpaRepository<Pinout, String> {

    @Query("SELECT NEW com.B1team.b01.dto.PinoutOutputDto(p.id, p.sort, p.productDate, m.name, p.productCnt, m.unit, m.location) " +
            "FROM Pinout p JOIN Materials m ON p.mtrId = m.id " +
            "WHERE (:sort IS NULL OR :sort = p.sort) " +
            "AND (:startDate IS NULL OR p.productDate >= :startDate) " +
            "AND (:endDate IS NULL OR p.productDate <= :endDate) " +
            "AND (:mtrName IS NULL OR m.name = :mtrName) " +
            "AND (:currentTime >= p.productDate) " +
            "ORDER BY p.productDate DESC")
    List<PinoutOutputDto> findPinoutsByConditions(@Param("sort") String sort,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate,
                                                  @Param("mtrName") String mtrName,
                                                  @Param("currentTime") LocalDateTime currentTime);

}
