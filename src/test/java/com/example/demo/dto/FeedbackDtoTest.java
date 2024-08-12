package com.example.demo.dto;

import com.example.demo.entity.ServiceTypeEntity;
import com.example.demo.entity.TechnicianDetailsEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testFeedbackDtoValid() {
        ServiceTypeEntity serviceTypeEntity = new ServiceTypeEntity();
        serviceTypeEntity.setId(1L);
        serviceTypeEntity.setServicename("Plumbing");

        TechnicianDetailsEntity technicianDetails = new TechnicianDetailsEntity();
        technicianDetails.setId(1L);
        technicianDetails.setTec_name("John Doe");
        technicianDetails.setTec_mobno("9855564881");

        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(1L);
        dto.setComment("Great service");
        dto.setRating("5");
        dto.setServiceTypeEntity(serviceTypeEntity);
        dto.setTechnicianDetailsEntity(technicianDetails);

        Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");

        Set<ConstraintViolation<ServiceTypeEntity>> serviceViolations = validator.validate(serviceTypeEntity);
        assertTrue(serviceViolations.isEmpty(), "There should be no validation errors");

        Set<ConstraintViolation<TechnicianDetailsEntity>> technicianViolations = validator.validate(technicianDetails);
        assertTrue(technicianViolations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testFeedbackDtoInvalid() {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setComment(" ");
        dto.setRating(" ");
        dto.setServiceTypeEntity(null);
        dto.setTechnicianDetailsEntity(null);

        Set<ConstraintViolation<FeedbackDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<FeedbackDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(4, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Comment cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Rating cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Service type details cannot be null")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Technician details cannot be null")));
    }
}
