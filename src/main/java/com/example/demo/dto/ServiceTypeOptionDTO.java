package com.example.demo.dto;

import com.example.demo.entity.VehicleDetailsEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceTypeOptionDTO {
    private long id;
    @NotBlank(message = "service name cannot be blank")
    private String servicename;
    @NotBlank(message = "service details cannot be blank")
    private String servicedetails;
    @NotBlank(message = "price cannot be blank")
    private String price;
    @NotNull(message = "vehicle details cannot be null")
    private VehicleDetailsEntity vehicleDetailsEntity;
}
