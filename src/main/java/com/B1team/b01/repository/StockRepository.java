package com.B1team.b01.repository;


import com.B1team.b01.dto.MaterialStockDto;
import com.B1team.b01.dto.StockListDto;
import com.B1team.b01.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {
    Stock findByMtrId(String mtrId);

    List<Object[]> findByProductIdNotNull();

    //제품 재고 조회 및 검색
    @Query("SELECT p.name, p.id, s.unit, p.price, p.sort, p.location " +
            "FROM Product p JOIN Stock s ON p.id = s.productId " +
            "WHERE (:productName IS NULL OR p.name = :productName) " +
            "AND (:productId IS NULL OR p.id = :productId) " +
            "AND (:productSort IS NULL OR p.sort = :productSort)")
    List<Object[]> getProductStockList(@Param("productName") String productName,
                                       @Param("productId") String productId,
                                       @Param("productSort") String productSort);

    @Query("SELECT st.ea FROM Stock st WHERE st.productId = :productId")
    Long findByStockCnt(String productId);


    //원자재 재고만 들고오기
    @Query("SELECT st.ea FROM Stock st")
    List<Long> findByMTRStockCnt();


    //원자재 재고 리스트
    @Query("SELECT NEW com.B1team.b01.dto.MaterialStockDto(m.name, s.ea, s.unit) " +
            "FROM Materials m " +
            "JOIN Stock s ON m.id = s.mtrId " +
            "WHERE s.mtrId IS NOT NULL " +
            "AND (:mtrName IS NULL OR m.name = :mtrName)" +
            "ORDER BY s.mtrId")
    List<MaterialStockDto> findMaterialStock(@Param("mtrName") String mtrName);
}
