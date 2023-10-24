package com.B1team.b01.repository;

import com.B1team.b01.dto.BomDto;
import com.B1team.b01.entity.BOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BomRepository extends JpaRepository<BOM, String> {
    @Query("select b from BOM b where b.productId= :id and b.mtrId not in('MTR44')")
    List<BOM> findPID(@Param("id") String pid);

    @Query("SELECT b.id, b.mtrId, b.mtrName, b.volume, p.id, p.name " +
            "FROM BOM b, Product p " +
            "WHERE b.productId = p.id " +
            "AND (:productName IS NULL OR p.name = :productName) " +
            "AND (:mtrName IS NULL OR b.mtrName = :mtrName)")
    List<Object[]> getBomList(@Param("productName") String productName,
                              @Param("mtrName") String mtrName);


}