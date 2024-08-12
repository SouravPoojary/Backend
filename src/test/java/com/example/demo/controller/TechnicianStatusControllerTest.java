package com.example.demo.controller;

import com.example.demo.dto.TechnicianStatusDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.TechnicianStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TechnicianStatusControllerTest {



    @Mock
    private TechnicianStatusService technicianStatusService;

    @InjectMocks
    private TechnicianStatusController technicianStatusController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void createSuccess() throws Exception {
        TechnicianStatusDTO dto = new TechnicianStatusDTO();
        dto.setId(1L);

        when(technicianStatusService.saveDetails(any(TechnicianStatusDTO.class))).thenReturn(dto);

        ResponseEntity<TechnicianStatusDTO> response = technicianStatusController.create(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAllSuccess() {
        TechnicianStatusDTO dto1 = new TechnicianStatusDTO();
        TechnicianStatusDTO dto2 = new TechnicianStatusDTO();
        List<TechnicianStatusDTO> list = Arrays.asList(dto1, dto2);

        when(technicianStatusService.getAll()).thenReturn(list);

        ResponseEntity<List<TechnicianStatusDTO>> response = technicianStatusController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        TechnicianStatusDTO dto = new TechnicianStatusDTO();
        dto.setId(1L);

        when(technicianStatusService.getById(anyLong())).thenReturn(dto);

        ResponseEntity<TechnicianStatusDTO> response = technicianStatusController.getById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByIdNotFound() throws UserNotFoundException {
        when(technicianStatusService.getById(anyLong())).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<TechnicianStatusDTO> response = null;
        try {
            response = technicianStatusController.getById(2L);
        } catch (UserNotFoundException e) {
            assertEquals("User not found", e.getMessage());

        }
        assertNull(response);

    }

    @Test
    void updateSuccess() throws UserNotFoundException {
        TechnicianStatusDTO dto = new TechnicianStatusDTO();
        dto.setId(1L);

        when(technicianStatusService.getById(anyLong())).thenReturn(dto);
        when(technicianStatusService.saveDetails(any(TechnicianStatusDTO.class))).thenReturn(dto);

        ResponseEntity<TechnicianStatusDTO> response = technicianStatusController.update(1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateNotFound() throws UserNotFoundException {
        TechnicianStatusDTO dto = new TechnicianStatusDTO();

        when(technicianStatusService.getById(anyLong())).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<TechnicianStatusDTO> response = null;
        try {
            response = technicianStatusController.update(1L, dto);
        } catch (UserNotFoundException e) {
            assertEquals("User not found", e.getMessage());
        }
        assertNull(response);

    }

    @Test
    void deleteSuccess() throws UserNotFoundException {
        doNothing().when(technicianStatusService).deleteById(anyLong());

        ResponseEntity<TechnicianStatusDTO> response = technicianStatusController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("User not found")).when(technicianStatusService).deleteById(anyLong());

        ResponseEntity<TechnicianStatusDTO> response = null;
        try {
            response = technicianStatusController.delete(1L);
        } catch (UserNotFoundException e) {
            assertEquals("User not found", e.getMessage());
        }
        assertNull(response);

    }
}
