package com.example.demo.controller;

import com.example.demo.dto.ServiceTypeOptionDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.ServiceTypeService;
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

class ServiceTypeControllerTest {

    @Mock
    private ServiceTypeService serviceTypeService;

    @InjectMocks
    private ServiceTypeController serviceTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSuccess() {
        ServiceTypeOptionDTO dto = new ServiceTypeOptionDTO();
        dto.setId(1L);

        when(serviceTypeService.saveDetails(any(ServiceTypeOptionDTO.class))).thenReturn(dto);

        ResponseEntity<ServiceTypeOptionDTO> response = serviceTypeController.create(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAllSuccess() {
        ServiceTypeOptionDTO dto1 = new ServiceTypeOptionDTO();
        ServiceTypeOptionDTO dto2 = new ServiceTypeOptionDTO();
        List<ServiceTypeOptionDTO> list = Arrays.asList(dto1, dto2);

        when(serviceTypeService.getAll()).thenReturn(list);

        ResponseEntity<List<ServiceTypeOptionDTO>> response = serviceTypeController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        ServiceTypeOptionDTO dto = new ServiceTypeOptionDTO();
        dto.setId(1L);

        when(serviceTypeService.getById(anyLong())).thenReturn(dto);

        ResponseEntity<ServiceTypeOptionDTO> response = serviceTypeController.getById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByIdNotFound() throws UserNotFoundException {
        when(serviceTypeService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            serviceTypeController.getById(1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void updateSuccess() throws UserNotFoundException {
        ServiceTypeOptionDTO dto = new ServiceTypeOptionDTO();
        dto.setId(1L);

        when(serviceTypeService.getById(anyLong())).thenReturn(dto);
        when(serviceTypeService.saveDetails(any(ServiceTypeOptionDTO.class))).thenReturn(dto);

        ResponseEntity<ServiceTypeOptionDTO> response = serviceTypeController.update(1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateNotFound() throws UserNotFoundException {
        ServiceTypeOptionDTO dto = new ServiceTypeOptionDTO();

        when(serviceTypeService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            serviceTypeController.update(1L, dto);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void deleteSuccess() throws UserNotFoundException {
        doNothing().when(serviceTypeService).deleteById(anyLong());

        ResponseEntity<ServiceTypeOptionDTO> response = serviceTypeController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Id not found")).when(serviceTypeService).deleteById(anyLong());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            serviceTypeController.delete(1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }
}
