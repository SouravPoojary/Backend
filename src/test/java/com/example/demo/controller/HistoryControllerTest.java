package com.example.demo.controller;

import com.example.demo.dto.ServiceHistoryDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.HistoryService;
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

class HistoryControllerTest {

    @Mock
    private HistoryService historyService;

    @InjectMocks
    private HistoryController historyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSuccess() {
        ServiceHistoryDTO dto = new ServiceHistoryDTO();
        dto.setId(1L);

        when(historyService.saveDetails(any(ServiceHistoryDTO.class))).thenReturn(dto);

        ResponseEntity<ServiceHistoryDTO> response = historyController.create(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAllSuccess() {
        ServiceHistoryDTO dto1 = new ServiceHistoryDTO();
        ServiceHistoryDTO dto2 = new ServiceHistoryDTO();
        List<ServiceHistoryDTO> list = Arrays.asList(dto1, dto2);

        when(historyService.getAll()).thenReturn(list);

        ResponseEntity<List<ServiceHistoryDTO>> response = historyController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        ServiceHistoryDTO dto = new ServiceHistoryDTO();
        dto.setId(1L);

        when(historyService.getById(anyLong())).thenReturn(dto);

        ResponseEntity<ServiceHistoryDTO> response = historyController.getById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByIdNotFound() throws UserNotFoundException {
        when(historyService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            historyController.getById(2L);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void updateSuccess() throws UserNotFoundException {
        ServiceHistoryDTO dto = new ServiceHistoryDTO();
        dto.setId(1L);

        when(historyService.getById(anyLong())).thenReturn(dto);
        when(historyService.saveDetails(any(ServiceHistoryDTO.class))).thenReturn(dto);

        ResponseEntity<ServiceHistoryDTO> response = historyController.update(1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateNotFound() throws UserNotFoundException {
        ServiceHistoryDTO dto = new ServiceHistoryDTO();

        when(historyService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            historyController.update(1L, dto);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void deleteSuccess() throws UserNotFoundException {
        doNothing().when(historyService).deleteById(anyLong());

        ResponseEntity<ServiceHistoryDTO> response = historyController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Id not found")).when(historyService).deleteById(anyLong());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            historyController.delete(1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }
}
