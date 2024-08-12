package com.example.demo.dto;

import com.example.demo.entity.VehicleOwnerEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleDetailsDTO {
    private long id;
    @NotBlank(message = "Company name cannot be blank")
    private String compname;
    @NotBlank(message = "Vehicle number cannot be blank")
    private String vehicleno;
    @NotNull(message = "Vehicle owner entity cannot be null")
    private VehicleOwnerEntity vehicleOwnerEntity;
}
