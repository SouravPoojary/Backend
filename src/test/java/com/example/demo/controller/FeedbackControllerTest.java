package com.example.demo.controller;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.FeedbackService;
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

class FeedbackControllerTest {

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSuccess() {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(1L);

        when(feedbackService.saveDetails(any(FeedbackDTO.class))).thenReturn(dto);

        ResponseEntity<FeedbackDTO> response = feedbackController.create(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAllSuccess() {
        FeedbackDTO dto1 = new FeedbackDTO();
        FeedbackDTO dto2 = new FeedbackDTO();
        List<FeedbackDTO> list = Arrays.asList(dto1, dto2);

        when(feedbackService.getAll()).thenReturn(list);

        ResponseEntity<List<FeedbackDTO>> response = feedbackController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(1L);

        when(feedbackService.getById(anyLong())).thenReturn(dto);

        ResponseEntity<FeedbackDTO> response = feedbackController.getById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getByIdNotFound() throws UserNotFoundException {
        when(feedbackService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));
        ResponseEntity<FeedbackDTO> response=null;
        try {
            response=feedbackController.getById(1L);
        }
        catch (UserNotFoundException exception){
            assertEquals("Id not found", exception.getMessage());

        }
        assertNull(response);

    }

    @Test
    void updateSuccess() throws UserNotFoundException {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(1L);

        when(feedbackService.getById(anyLong())).thenReturn(dto);
        when(feedbackService.saveDetails(any(FeedbackDTO.class))).thenReturn(dto);

        ResponseEntity<FeedbackDTO> response = feedbackController.update(1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateNotFound() throws UserNotFoundException {
        FeedbackDTO dto = new FeedbackDTO();

        when(feedbackService.getById(anyLong())).thenThrow(new UserNotFoundException("Id not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            feedbackController.update(1L, dto);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    void deleteSuccess() throws UserNotFoundException {
        doNothing().when(feedbackService).deleteById(anyLong());

        ResponseEntity<FeedbackDTO> response = feedbackController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("Id not found")).when(feedbackService).deleteById(anyLong());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            feedbackController.delete(1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }
}
