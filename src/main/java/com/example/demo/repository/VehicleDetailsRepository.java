package com.example.demo.repository;

import com.example.demo.entity.VehicleDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleDetailsRepository extends JpaRepository<VehicleDetailsEntity,Long> {
}
