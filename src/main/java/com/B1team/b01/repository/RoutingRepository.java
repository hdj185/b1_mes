package com.B1team.b01.repository;

import com.B1team.b01.entity.Routing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutingRepository extends JpaRepository<Routing, String> {
    List<Routing> findByProductIdOrderByOrder(String productId);

}
