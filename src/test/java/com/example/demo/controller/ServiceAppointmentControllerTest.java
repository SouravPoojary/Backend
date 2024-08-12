package com.example.demo.controller;

import com.example.demo.dto.ServiceAppointmentDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.AppointmentService;
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

class ServiceAppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private ServiceAppointmentController serviceAppointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSuccess() {
        ServiceAppointmentDTO dto = new ServiceAppointmentDTO();
        dto.setId(1L);

        when(appointmentService.saveDetails(any(ServiceAppointmentDTO.class))).thenReturn(dto);

        ResponseEntity<ServiceAppointmentDTO> response = serviceAppointmentController.create(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAllSuccess() {
        ServiceAppointmentDTO dto1 = new ServiceAppointmentDTO();
        ServiceAppointmentDTO dto2 = new ServiceAppointmentDTO();
        List<ServiceAppointmentDTO> list = Arrays.asList(dto1, dto2);

        when(appointmentService.getAll()).thenReturn(list);

        ResponseEntity<List<ServiceAppointmentDTO>> response = serviceAppointmentController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        ServiceAppointmentDTO dto = new ServiceAppointmentDTO();
        dto.setId(1L);

        when(appointmentService.getById(anyLong())).thenReturn(dto);

        ResponseEntity<ServiceAppointmentDTO> response = serviceAppointmentController.getById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByIdNotFound() throws UserNotFoundException {
        when(appointmentService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            serviceAppointmentController.getById(1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void updateSuccess() throws UserNotFoundException {
        ServiceAppointmentDTO dto = new ServiceAppointmentDTO();
        dto.setId(1L);

        when(appointmentService.getById(anyLong())).thenReturn(dto);
        when(appointmentService.saveDetails(any(ServiceAppointmentDTO.class))).thenReturn(dto);

        ResponseEntity<ServiceAppointmentDTO> response = serviceAppointmentController.update(1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateNotFound() throws UserNotFoundException {
        ServiceAppointmentDTO dto = new ServiceAppointmentDTO();

        when(appointmentService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            serviceAppointmentController.update(1L, dto);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void deleteSuccess() throws UserNotFoundException {
        doNothing().when(appointmentService).deleteById(anyLong());

        ResponseEntity<ServiceAppointmentDTO> response = serviceAppointmentController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Id not found")).when(appointmentService).deleteById(anyLong());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            serviceAppointmentController.delete(1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }
}
