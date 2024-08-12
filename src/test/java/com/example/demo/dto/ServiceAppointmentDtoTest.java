package com.example.demo.dto;

import com.example.demo.entity.ServiceTypeEntity;
import com.example.demo.entity.TechnicianStatusEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceAppointmentDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testServiceAppointmentDtoValid() {
        ServiceTypeEntity serviceTypeEntity = new ServiceTypeEntity();
        serviceTypeEntity.setId(1L);
        serviceTypeEntity.setServicename("Plumbing");

        TechnicianStatusEntity technicianStatus = new TechnicianStatusEntity();
        technicianStatus.setId(1L);
        technicianStatus.setStatus("Available");

        ServiceAppointmentDTO dto = new ServiceAppointmentDTO();
        dto.setId(1L);
        dto.setAppDate("2024-07-20");
        dto.setServiceTypeEntity(serviceTypeEntity);
        dto.setTechnicianStatusEntity(technicianStatus);

        Set<ConstraintViolation<ServiceAppointmentDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");

        Set<ConstraintViolation<ServiceTypeEntity>> serviceViolations = validator.validate(serviceTypeEntity);
        assertTrue(serviceViolations.isEmpty(), "There should be no validation errors");

        Set<ConstraintViolation<TechnicianStatusEntity>> technicianViolations = validator.validate(technicianStatus);
        assertTrue(technicianViolations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testServiceAppointmentDtoInvalid() {
        ServiceAppointmentDTO dto = new ServiceAppointmentDTO();
        dto.setAppDate("");
        dto.setServiceTypeEntity(null);
        dto.setTechnicianStatusEntity(null);

        Set<ConstraintViolation<ServiceAppointmentDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<ServiceAppointmentDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(3, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Appointment date cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Service type cannot be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Technician status cannot be null")));
    }
}

