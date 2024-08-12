package com.example.demo.dto;

import com.example.demo.entity.TechnicianDetailsEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TechnicianStatusDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTechnicianStatusDtoValid() {
        TechnicianStatusDTO dto = new TechnicianStatusDTO();
        dto.setId(1L);
        dto.setStatus("Active");
        TechnicianDetailsEntity technicianDetails=new TechnicianDetailsEntity();
        technicianDetails.setId(1L);
        technicianDetails.setTec_name("John Doe");
        technicianDetails.setTec_mobno("9855564881");


        Set<ConstraintViolation<TechnicianStatusDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
        Set<ConstraintViolation<TechnicianDetailsEntity>> violations1=validator.validate(technicianDetails);
        assertTrue(violations1.isEmpty(),"There should be no validation errors");
    }

    @Test
    public void testTechnicianStatusDtoInvalid() {
        TechnicianStatusDTO dto = new TechnicianStatusDTO();
        dto.setStatus(" ");
        dto.setTechnicianDetailsEntity(null);

        Set<ConstraintViolation<TechnicianStatusDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation errors");


        for (ConstraintViolation<TechnicianStatusDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }



        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Status cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Technician detail cannot be null")));
//        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Mobno cannot be blank")));
    }
}
