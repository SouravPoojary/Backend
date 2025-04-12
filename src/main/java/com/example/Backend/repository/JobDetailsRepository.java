package com.example.Backend.repository;

import com.example.Backend.entity.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDetailsRepository extends JpaRepository<JobDetails,Long> {
}
