package com.example.demo.controller;

import com.example.demo.dto.VehicleOwnerDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.VehicleOwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class VehicleOwnerControllerTest {
    @Mock
    VehicleOwnerService vehicleOwnerService;

    @InjectMocks
    private VehicleOwnerController vehicleOwnerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSuccess() {
        VehicleOwnerDTO dto = new VehicleOwnerDTO();
        VehicleOwnerDTO dto1 = new VehicleOwnerDTO();
        List<VehicleOwnerDTO> dtos = Arrays.asList(dto, dto1);

        when(vehicleOwnerService.getAll()).thenReturn(dtos);

        ResponseEntity<List<VehicleOwnerDTO>> response = vehicleOwnerController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    public void testCreateSuccess() {
        VehicleOwnerDTO dto = new VehicleOwnerDTO();
        VehicleOwnerDTO dto1 = new VehicleOwnerDTO();

        when(vehicleOwnerService.saveDetails(any(VehicleOwnerDTO.class))).thenReturn(dto1);

        ResponseEntity<VehicleOwnerDTO> response = vehicleOwnerController.create(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto1, response.getBody());
    }

    @Test
    public void testDeletevehicleOwnerSuccess() throws UserNotFoundException {
        ResponseEntity<String> response = vehicleOwnerController.delete(1L);
        assertEquals("Deleted successfully", response.getBody());
    }

    @Test
    public void testDeletevehicleOwnerNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Id not Found")).when(vehicleOwnerService).deleteById(1L);

        try {
            vehicleOwnerController.delete(1L);
        } catch (UserNotFoundException e) {
            assertEquals("Id not Found", e.getMessage());
        }
    }

    @Test
    public void testUpdatevehicleOwner() throws UserNotFoundException, ParseException {
        VehicleOwnerDTO dto = new VehicleOwnerDTO();
        dto.setId(1L);


        when(vehicleOwnerService.saveDetails(any(VehicleOwnerDTO.class))).thenReturn(dto);

        ResponseEntity<VehicleOwnerDTO> response = vehicleOwnerController.update(1l,dto);

        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
