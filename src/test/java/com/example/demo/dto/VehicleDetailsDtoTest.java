package com.example.demo.dto;

import com.example.demo.entity.VehicleOwnerEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleDetailsDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testVehicleDetailsDtoValid() {
        VehicleOwnerEntity vehicleOwnerEntity = new VehicleOwnerEntity();
        vehicleOwnerEntity.setId(1L);
        // Set other necessary fields for a valid VehicleOwnerEntity

        VehicleDetailsDTO dto = new VehicleDetailsDTO();
        dto.setId(1L);
        dto.setCompname("Toyota");
        dto.setVehicleno("KA-01-1234");
        dto.setVehicleOwnerEntity(vehicleOwnerEntity);

        Set<ConstraintViolation<VehicleDetailsDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");

        Set<ConstraintViolation<VehicleOwnerEntity>> ownerViolations = validator.validate(vehicleOwnerEntity);
        assertTrue(ownerViolations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testVehicleDetailsDtoInvalid() {
        VehicleDetailsDTO dto = new VehicleDetailsDTO();
        dto.setCompname("");
        dto.setVehicleno("");
        dto.setVehicleOwnerEntity(null);

        Set<ConstraintViolation<VehicleDetailsDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<VehicleDetailsDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(3, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Company name cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Vehicle number cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Vehicle owner entity cannot be null")));
    }
}
