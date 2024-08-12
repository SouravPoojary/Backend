package com.example.demo.dto;

import com.example.demo.Address;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TechnicianDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTechnicianDtoValid() {
        Address address = new Address();
        address.setCity("Udupi");
        address.setStreet("Church Street");
        address.setPincode("576101");

        TechnicianDTO dto = new TechnicianDTO();
        dto.setId(1L);
        dto.setTec_name("John Doe");
        dto.setTec_mobno("+1234567890");
        dto.setAddress(address);

        Set<ConstraintViolation<TechnicianDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");

        Set<ConstraintViolation<Address>> addressViolations = validator.validate(address);
        assertTrue(addressViolations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testTechnicianDtoInvalid() {
        TechnicianDTO dto = new TechnicianDTO();
        dto.setTec_name(" ");
        dto.setTec_mobno("6.3");
        dto.setAddress(null);

        Set<ConstraintViolation<TechnicianDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<TechnicianDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(3, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Name cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("must match \"^\\+?[0-9. ()-]{7,25}$\"")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("address cannot be null")));
    }
}
