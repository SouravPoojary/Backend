package com.example.demo.dto;

import com.example.demo.entity.VehicleDetailsEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTypeOptionDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testServiceTypeOptionDtoValid() {
        VehicleDetailsEntity vehicleDetailsEntity = new VehicleDetailsEntity();
        vehicleDetailsEntity.setId(1L);
        // Set other necessary fields for a valid VehicleDetailsEntity

        ServiceTypeOptionDTO dto = new ServiceTypeOptionDTO();
        dto.setId(1L);
        dto.setServicename("Oil Change");
        dto.setServicedetails("Change engine oil");
        dto.setPrice("50");
        dto.setVehicleDetailsEntity(vehicleDetailsEntity);

        Set<ConstraintViolation<ServiceTypeOptionDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");

        Set<ConstraintViolation<VehicleDetailsEntity>> vehicleViolations = validator.validate(vehicleDetailsEntity);
        assertTrue(vehicleViolations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testServiceTypeOptionDtoInvalid() {
        ServiceTypeOptionDTO dto = new ServiceTypeOptionDTO();
        dto.setServicename("");
        dto.setServicedetails("");
        dto.setPrice(" ");
        dto.setVehicleDetailsEntity(null);

        Set<ConstraintViolation<ServiceTypeOptionDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<ServiceTypeOptionDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(4, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("service name cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("service details cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("price cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("vehicle details cannot be null")));
    }
}
