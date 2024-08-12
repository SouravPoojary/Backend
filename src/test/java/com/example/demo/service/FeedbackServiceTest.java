package com.example.demo.service;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.entity.FeedbackEntity;
import com.example.demo.entity.ServiceTypeEntity;
import com.example.demo.entity.TechnicianDetailsEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    private FeedbackEntity feedbackEntity;
    private FeedbackDTO feedbackDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ServiceTypeEntity serviceTypeEntity = new ServiceTypeEntity();
        serviceTypeEntity.setId(1L);
        serviceTypeEntity.setServicename("Oil Change");

        TechnicianDetailsEntity technicianDetailsEntity = new TechnicianDetailsEntity();
        technicianDetailsEntity.setId(1L);
        technicianDetailsEntity.setTec_name("John Doe");

        feedbackEntity = new FeedbackEntity();
        feedbackEntity.setId(1L);
        feedbackEntity.setComment("Great service!");
        feedbackEntity.setRating("5");
        feedbackEntity.setServiceTypeEntity(serviceTypeEntity);
        feedbackEntity.setTechnicianDetailsEntity(technicianDetailsEntity);

        feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId(1L);
        feedbackDTO.setComment("Great service!");
        feedbackDTO.setRating("5");
        feedbackDTO.setServiceTypeEntity(serviceTypeEntity);
        feedbackDTO.setTechnicianDetailsEntity(technicianDetailsEntity);
    }

    @Test
    void DtoToEntity() {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(1L);
        dto.setComment("Great service!");
        dto.setRating("5");

        FeedbackEntity entity = feedbackService.DtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getComment(), entity.getComment());
        assertEquals(dto.getRating(), entity.getRating());
    }

    @Test
    void EntityToDto() {
        FeedbackEntity entity = new FeedbackEntity();
        entity.setId(1L);
        entity.setComment("Great service!");
        entity.setRating("5");

        FeedbackDTO dto = feedbackService.EntityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getComment(), dto.getComment());
        assertEquals(entity.getRating(), dto.getRating());
    }

    @Test
    void saveDetailsSuccess() {
        BeanUtils.copyProperties(feedbackDTO, feedbackEntity);
        when(feedbackRepository.save(any(FeedbackEntity.class))).thenReturn(feedbackEntity);

        FeedbackDTO savedDto = feedbackService.saveDetails(feedbackDTO);

        assertNotNull(savedDto);
        assertEquals(feedbackDTO.getId(), savedDto.getId());
        verify(feedbackRepository, times(1)).save(any(FeedbackEntity.class));
    }

    @Test
    void getAllSuccess() {
        List<FeedbackEntity> feedbackList = new ArrayList<>();
        feedbackList.add(feedbackEntity);

        when(feedbackRepository.findAll()).thenReturn(feedbackList);

        List<FeedbackDTO> dtoList = feedbackService.getAll();

        assertNotNull(dtoList);
        assertFalse(dtoList.isEmpty());
        assertEquals(1, dtoList.size());
        assertEquals(feedbackEntity.getId(), feedbackDTO.getId());
        assertEquals(feedbackEntity.getComment(), feedbackDTO.getComment());
        assertEquals(feedbackEntity.getRating(), feedbackDTO.getRating());
        verify(feedbackRepository, times(1)).findAll();
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        when(feedbackRepository.findById(anyLong())).thenReturn(Optional.of(feedbackEntity));

        FeedbackDTO dto = feedbackService.getById(1L);

        assertNotNull(dto);
        assertEquals(feedbackEntity.getId(), dto.getId());
        assertEquals(feedbackEntity.getComment(), dto.getComment());
        assertEquals(feedbackEntity.getRating(), dto.getRating());
        verify(feedbackRepository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdNotFound() {
        when(feedbackRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            feedbackService.getById(1L);
        });

        verify(feedbackRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteByIdSuccess() throws UserNotFoundException {
        when(feedbackRepository.findById(anyLong())).thenReturn(Optional.of(feedbackEntity));
        doNothing().when(feedbackRepository).deleteById(anyLong());

        feedbackService.deleteById(1L);

        verify(feedbackRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNotFound() {
        when(feedbackRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            feedbackService.deleteById(1L);
        });

        verify(feedbackRepository, times(1)).findById(anyLong());
        verify(feedbackRepository, times(0)).deleteById(anyLong());
    }
}
