package com.example.demo.controller;

import com.example.demo.dto.TechnicianDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.TechnicianDetailsService;
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

class TechnicianDetailControllerTest {

    @Mock
    private TechnicianDetailsService technicianDetailsService;

    @InjectMocks
    private TechnicianDetailController technicianDetailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSuccess() {
        TechnicianDTO dto = new TechnicianDTO();
        dto.setId(1L);

        when(technicianDetailsService.saveDetails(any(TechnicianDTO.class))).thenReturn(dto);

        ResponseEntity<TechnicianDTO> response = technicianDetailController.create(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAllSuccess() {
        TechnicianDTO dto1 = new TechnicianDTO();
        TechnicianDTO dto2 = new TechnicianDTO();
        List<TechnicianDTO> list = Arrays.asList(dto1, dto2);

        when(technicianDetailsService.getAll()).thenReturn(list);

        ResponseEntity<List<TechnicianDTO>> response = technicianDetailController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        TechnicianDTO dto = new TechnicianDTO();
        dto.setId(1L);

        when(technicianDetailsService.getById(anyLong())).thenReturn(dto);

        ResponseEntity<TechnicianDTO> response = technicianDetailController.getById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByIdNotFound() throws UserNotFoundException {
        when(technicianDetailsService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            technicianDetailController.getById(1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void updateSuccess() throws UserNotFoundException {
        TechnicianDTO dto = new TechnicianDTO();
        dto.setId(1L);

        when(technicianDetailsService.getById(anyLong())).thenReturn(dto);
        when(technicianDetailsService.saveDetails(any(TechnicianDTO.class))).thenReturn(dto);

        ResponseEntity<TechnicianDTO> response = technicianDetailController.update(1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateNotFound() throws UserNotFoundException {
        TechnicianDTO dto = new TechnicianDTO();

        when(technicianDetailsService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            technicianDetailController.update(1L, dto);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void deleteSuccess() throws UserNotFoundException {
        doNothing().when(technicianDetailsService).deleteById(anyLong());

        ResponseEntity<TechnicianDTO> response = technicianDetailController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Id not found")).when(technicianDetailsService).deleteById(anyLong());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            technicianDetailController.delete(1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }
}
