package com.B1team.b01.repository;

import com.B1team.b01.entity.Wplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.List;

public interface ProductionRepository extends JpaRepository<Wplan,String>, JpaSpecificationExecutor<Wplan> {
    List<Wplan> findAll();
}
