package com.example.demo.dto;

import com.example.demo.entity.ServiceAppointmentEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ServiceHistoryDTO {
    private long id;
    @NotNull(message = "service appointment details cannot be null")
    private ServiceAppointmentEntity serviceAppointmentEntity;

}
