package com.B1team.b01.repository;

import com.B1team.b01.entity.Mprocess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MprocessRepository extends JpaRepository<Mprocess, String> {
    List<Mprocess> findAll();
}
