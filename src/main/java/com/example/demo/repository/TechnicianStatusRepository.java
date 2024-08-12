package com.example.demo.repository;

import com.example.demo.entity.TechnicianStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicianStatusRepository extends JpaRepository<TechnicianStatusEntity,Long> {
}
