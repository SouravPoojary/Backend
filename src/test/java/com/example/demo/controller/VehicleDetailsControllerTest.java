package com.example.demo.controller;

import com.example.demo.dto.VehicleDetailsDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.VehicleDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class VehicleDetailsControllerTest {

    @Mock
    private VehicleDetailsService vehicleDetailsService;

    @InjectMocks
    private VehicleDetailsController vehicleDetailsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSuccess() {
        VehicleDetailsDTO dto = new VehicleDetailsDTO();
        dto.setId(1L);

        when(vehicleDetailsService.saveDetails(any(VehicleDetailsDTO.class))).thenReturn(dto);

        ResponseEntity<VehicleDetailsDTO> response = vehicleDetailsController.create(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAllSuccess() {
        VehicleDetailsDTO dto1 = new VehicleDetailsDTO();
        VehicleDetailsDTO dto2 = new VehicleDetailsDTO();
        List<VehicleDetailsDTO> list = Arrays.asList(dto1, dto2);

        when(vehicleDetailsService.getAll()).thenReturn(list);

        ResponseEntity<List<VehicleDetailsDTO>> response = vehicleDetailsController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        VehicleDetailsDTO dto = new VehicleDetailsDTO();
        dto.setId(1L);

        when(vehicleDetailsService.getById(anyLong())).thenReturn(dto);

        ResponseEntity<VehicleDetailsDTO> response = vehicleDetailsController.getById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByIdNotFound() throws UserNotFoundException {
        when(vehicleDetailsService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            vehicleDetailsController.getById(1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void updateSuccess() throws UserNotFoundException {
        VehicleDetailsDTO dto = new VehicleDetailsDTO();
        dto.setId(1L);

        when(vehicleDetailsService.getById(anyLong())).thenReturn(dto);
        when(vehicleDetailsService.saveDetails(any(VehicleDetailsDTO.class))).thenReturn(dto);

        ResponseEntity<VehicleDetailsDTO> response = vehicleDetailsController.update(1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateNotFound() throws UserNotFoundException {
        VehicleDetailsDTO dto = new VehicleDetailsDTO();

        when(vehicleDetailsService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            vehicleDetailsController.update(1L, dto);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void deleteSuccess() throws UserNotFoundException {
        doNothing().when(vehicleDetailsService).deleteById(anyLong());

        ResponseEntity<VehicleDetailsDTO> response = vehicleDetailsController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Id not found")).when(vehicleDetailsService).deleteById(anyLong());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            vehicleDetailsController.delete(1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }
}
