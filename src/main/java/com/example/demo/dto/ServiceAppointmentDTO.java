package com.example.demo.dto;
import java.util.Date;

import com.example.demo.entity.ServiceTypeEntity;
import com.example.demo.entity.TechnicianDetailsEntity;
import com.example.demo.entity.TechnicianStatusEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceAppointmentDTO {
    private Long id;
    private String appDate;
    private ServiceTypeEntity serviceTypeEntity;
    private TechnicianDetailsEntity technicianDetailsEntity;

}