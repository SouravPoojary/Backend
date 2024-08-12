package com.example.demo.repository;

import com.example.demo.entity.ServiceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceTypeEntity,Long> {
}
