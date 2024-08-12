package com.example.demo.dto;

import com.example.demo.resource.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserDtoValid() {
        UserDto dto = new UserDto();
        dto.setId(1);
        dto.setName("John Doe");
        dto.setUsername("johndoe");
        dto.setPassword("password123");
        dto.setRole(Role.ADMIN);

        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    public void testUserDtoInvalid() {
        UserDto dto = new UserDto();
        dto.setId(1);
        dto.setName("");
        dto.setUsername("");
        dto.setPassword("");
        dto.setRole(null);

        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "There should be validation errors");

        for (ConstraintViolation<UserDto> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertEquals(4, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("name cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("username cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("password cannot be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Role should be mentioned")));
    }
}
