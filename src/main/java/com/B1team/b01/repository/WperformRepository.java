package com.B1team.b01.repository;


import com.B1team.b01.dto.WperformDto;
import com.B1team.b01.dto.WplanDto;
import com.B1team.b01.entity.Wperform;
import com.B1team.b01.entity.Wplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface WperformRepository extends JpaRepository<Wperform, String> , JpaSpecificationExecutor<Wperform> {
    List<Wperform> findAll();
}
