package com.example.demo.repository;

import com.example.demo.entity.ServiceAppointmentEntity;
import com.example.demo.entity.ServiceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceAppointmentRepository extends JpaRepository<ServiceAppointmentEntity,Long> {
    Optional<ServiceAppointmentEntity> findByServiceTypeEntity(ServiceTypeEntity id);
}
