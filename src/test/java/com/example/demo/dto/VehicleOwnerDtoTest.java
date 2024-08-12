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

public class VehicleOwnerDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
        validator=factory.getValidator();
    }

    @Test
    public  void testVehicleOwnerDtoValid() {
        Address address = new Address();
        address.setCity("Hebri");
        address.setStreet("2 cross");
        address.setPincode("576122");

        VehicleOwnerDTO dto = new VehicleOwnerDTO();
        dto.setOwner_name("Sourav");
        dto.setOwner_mobno("9008121697");
        dto.setOwner_email("sourav@gmail.com");
        dto.setAddress(address);

        Set<ConstraintViolation<VehicleOwnerDTO>> voilations = validator.validate(dto);
        assertTrue(voilations.isEmpty(), "There should be no validation errors");
    }
    @Test
    public void testVehicleOwnerDtoInValid(){
        VehicleOwnerDTO dto=new VehicleOwnerDTO();
        dto.setOwner_name("");
        dto.setOwner_mobno("525");
        dto.setOwner_email("sss");
        dto.setAddress(new Address());
        Set<ConstraintViolation<VehicleOwnerDTO>> voilations=validator.validate(dto);
        assertFalse(voilations.isEmpty(),"There should be validation errors");

        for (ConstraintViolation<VehicleOwnerDTO> voilation:voilations){
            System.out.println(voilation.getMessage());
        }
        assertEquals(3,voilations.size());
        assertTrue(voilations.stream().anyMatch(v->v.getMessage().equals("Owner name cannot be blank")));
        assertTrue(voilations.stream().anyMatch(v->v.getMessage().equals("Email cannot be empty")));
        assertTrue(voilations.stream().anyMatch(v->v.getMessage().equals("Mob no cannot be invalid")));



    }
}
