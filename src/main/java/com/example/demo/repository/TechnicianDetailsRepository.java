package com.example.demo.repository;

import com.example.demo.entity.TechnicianDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicianDetailsRepository extends JpaRepository<TechnicianDetailsEntity,Long> {
}
