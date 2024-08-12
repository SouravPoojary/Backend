package com.example.demo.service;

import com.example.demo.dto.ServiceHistoryDTO;
import com.example.demo.entity.HistoryEntity;
import com.example.demo.entity.ServiceAppointmentEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ServiceHistoryRepository;
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

public class HistoryServiceTest {

    @Mock
    private ServiceHistoryRepository serviceHistoryRepository;

    @InjectMocks
    private HistoryService historyService;

    private HistoryEntity historyEntity;
    private ServiceHistoryDTO serviceHistoryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ServiceAppointmentEntity serviceAppointmentEntity = new ServiceAppointmentEntity();
        serviceAppointmentEntity.setId(1L);

        historyEntity = new HistoryEntity();
        historyEntity.setId(1L);
        historyEntity.setServiceAppointmentEntity(serviceAppointmentEntity);

        serviceHistoryDTO = new ServiceHistoryDTO();
        serviceHistoryDTO.setId(1L);
        serviceHistoryDTO.setServiceAppointmentEntity(serviceAppointmentEntity);
    }

    @Test
    void DtoToEntity() {
        ServiceHistoryDTO dto = new ServiceHistoryDTO();
        dto.setId(1L);

        HistoryEntity entity = historyService.DtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
    }

    @Test
    void EntityToDto() {
        HistoryEntity entity = new HistoryEntity();
        entity.setId(1L);

        ServiceHistoryDTO dto = historyService.EntityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
    }

    @Test
    void saveDetailsSuccess() {
        BeanUtils.copyProperties(serviceHistoryDTO, historyEntity);
        when(serviceHistoryRepository.save(any(HistoryEntity.class))).thenReturn(historyEntity);

        ServiceHistoryDTO savedDto = historyService.saveDetails(serviceHistoryDTO);

        assertNotNull(savedDto);
        assertEquals(serviceHistoryDTO.getId(), savedDto.getId());
        verify(serviceHistoryRepository, times(1)).save(any(HistoryEntity.class));
    }

    @Test
    void getAllSuccess() {
        List<HistoryEntity> historyList = new ArrayList<>();
        historyList.add(historyEntity);

        when(serviceHistoryRepository.findAll()).thenReturn(historyList);

        List<ServiceHistoryDTO> dtoList = historyService.getAll();

        assertNotNull(dtoList);
        assertFalse(dtoList.isEmpty());
        assertEquals(1, dtoList.size());
        assertEquals(historyEntity.getId(), serviceHistoryDTO.getId());
        verify(serviceHistoryRepository, times(1)).findAll();
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        when(serviceHistoryRepository.findById(anyLong())).thenReturn(Optional.of(historyEntity));

        ServiceHistoryDTO dto = historyService.getById(1L);

        assertNotNull(dto);
        assertEquals(historyEntity.getId(), dto.getId());
        verify(serviceHistoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdNotFound() {
        when(serviceHistoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            historyService.getById(1L);
        });

        verify(serviceHistoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteByIdSuccess() throws UserNotFoundException {
        when(serviceHistoryRepository.findById(anyLong())).thenReturn(Optional.of(historyEntity));
        doNothing().when(serviceHistoryRepository).deleteById(anyLong());

        historyService.deleteById(1L);

        verify(serviceHistoryRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNotFound() {
        when(serviceHistoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            historyService.deleteById(1L);
        });

        verify(serviceHistoryRepository, times(1)).findById(anyLong());
        verify(serviceHistoryRepository, times(0)).deleteById(anyLong());
    }
}
