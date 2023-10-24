package com.B1team.b01.repository;

import com.B1team.b01.entity.Finfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinfoRepository extends JpaRepository<Finfo, String> {
    List<Finfo> findByNameIn(List<String> name);
    List<Finfo> findByName(String name);

    //설비 정보 리스트
    @Query("SELECT f FROM Finfo f " +
            "WHERE (:name IS NULL OR f.name = :name) " +
            "AND (:location IS NULL OR f.location = :location ) " +
            "AND (:id IS NULL OR f.id = :id )")
    List<Finfo> findFinfosByConditions(@Param("name") String name,
                                       @Param("location") String location,
                                       @Param("id") String id);
}
