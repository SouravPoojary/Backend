package com.example.demo.dto;

import com.example.demo.entity.TechnicianDetailsEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class TechnicianStatusDTO {
    private long id;
    @NotBlank(message = "Status cannot be blank")
    private String status;
    private Date updateDate;
    @NotNull(message = "Technician detail cannot be null")
    private TechnicianDetailsEntity technicianDetailsEntity;
//    private Long serviceAppointmentEntity;
}
