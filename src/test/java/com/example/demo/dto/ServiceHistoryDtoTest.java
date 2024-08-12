package com.example.demo.dto;

import com.example.demo.entity.ServiceAppointmentEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceHistoryDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testServiceHistoryDtoValid() {
        ServiceAppointmentEntity serviceAppointmentEntity = new ServiceAppointmentEntity();
        serviceAppointmentEntity.setId(1L);
        // Set other necessary fields for a valid ServiceAppointmentEntity

        ServiceHistoryDTO dto = new ServiceHistoryDTO();
        dto.setId(1L);
        dto.setServiceAppointmentEntity(serviceAppointmentEntity);

        Set<ConstraintViolation<ServiceHistoryDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");

        Set<ConstraintViolation<ServiceAppointmentEntity>> serviceViolations = validator.validate(serviceAppointmentEntity);
        assertTrue(serviceViolations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testServiceHistoryDtoInvalid() {
        ServiceHistoryDTO dto = new ServiceHistoryDTO();
        dto.setServiceAppointmentEntity(null);

        Set<ConstraintViolation<ServiceHistoryDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<ServiceHistoryDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("service appointment details cannot be null")));
    }
}
