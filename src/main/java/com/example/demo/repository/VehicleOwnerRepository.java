package com.example.demo.repository;

import com.example.demo.entity.VehicleOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleOwnerRepository extends JpaRepository<VehicleOwnerEntity,Long> {
}
