package com.example.demo.repository;

import com.example.demo.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceHistoryRepository extends JpaRepository<HistoryEntity,Long> {
}
