package com.B1team.b01.repository;

import com.B1team.b01.dto.ProductDto;
import com.B1team.b01.entity.Materials;
import com.B1team.b01.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findAll(); //전체 품목 리스트 가져오는거

//    @Query("SELECT p FROM Product p " +
//            "WHERE p.productName LIKE CONCAT('%', :productName, '%') " +
//            "AND p.productId = :productId " +
//            "AND p.productSort LIKE CONCAT('%', :productSort, '%')")


    @Query("SELECT name, id, price, sort, location " +
            "FROM Product " +
            "WHERE (:productName IS NULL OR name = :productName) " +
            "AND (:productId IS NULL OR id = :productId) " +
            "AND (:productSort IS NULL OR sort = :productSort)")
    List<Object[]> getProductList(@Param("productName") String productName,
                                       @Param("productId") String productId,
                                       @Param("productSort") String productSort);


}
