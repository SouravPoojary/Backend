package com.example.demo.service;

import com.example.demo.dto.TechnicianStatusDTO;
import com.example.demo.entity.TechnicianStatusEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.TechnicianStatusRepository;
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

class TechnicianStatusServiceTest {

    @Mock
    private TechnicianStatusRepository technicianStatusRepository;

    @InjectMocks
    private TechnicianStatusService technicianStatusService;

    private TechnicianStatusEntity technicianStatusEntity;
    private TechnicianStatusDTO technicianStatusDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        technicianStatusEntity = new TechnicianStatusEntity();
        technicianStatusEntity.setId(1L);
        technicianStatusEntity.setStatus("Available");

        technicianStatusDTO = new TechnicianStatusDTO();
        technicianStatusDTO.setId(1L);
        technicianStatusDTO.setStatus("Available");

    }
    @Test
    void EntityToDto() {
        TechnicianStatusEntity technicianStatus = new TechnicianStatusEntity();
        technicianStatus.setId(1L);
        technicianStatus.setStatus("Available");

        TechnicianStatusDTO dto = technicianStatusService.EntityToDto(technicianStatus);

        assertNotNull(dto);
        assertEquals(technicianStatus.getId(), dto.getId());
        assertEquals(technicianStatus.getStatus(),dto.getStatus());
    }

    @Test
    void DtoToEntity() {
        TechnicianStatusDTO dto = new TechnicianStatusDTO();
        dto.setId(1L);
        dto.setStatus("Available");

        TechnicianStatusEntity technicianStatus = technicianStatusService.DtoToEntity(dto);

        assertNotNull(technicianStatus);
        assertEquals(dto.getId(), technicianStatus.getId());
        assertEquals(dto.getStatus(),technicianStatus.getStatus());
    }

    @Test
    void saveDetailsSuccess() {
        BeanUtils.copyProperties(technicianStatusDTO, technicianStatusEntity);
        when(technicianStatusRepository.save(any(TechnicianStatusEntity.class))).thenReturn(technicianStatusEntity);

        TechnicianStatusDTO savedDto = technicianStatusService.saveDetails(technicianStatusDTO);

        assertNotNull(savedDto);
        assertEquals(technicianStatusDTO.getId(), savedDto.getId());
        verify(technicianStatusRepository, times(1)).save(any(TechnicianStatusEntity.class));
    }

    @Test
    void getAllSuccess() {
        List<TechnicianStatusEntity> technicianStatusList = new ArrayList<>();
        technicianStatusList.add(technicianStatusEntity);

        when(technicianStatusRepository.findAll()).thenReturn(technicianStatusList);

        List<TechnicianStatusDTO> dtoList = technicianStatusService.getAll();

        assertNotNull(dtoList);
        assertFalse(dtoList.isEmpty());
        assertEquals(1, dtoList.size());
        assertEquals(technicianStatusEntity.getId(),technicianStatusDTO.getId());
        assertEquals(technicianStatusEntity.getStatus(),technicianStatusDTO.getStatus());
        verify(technicianStatusRepository, times(1)).findAll();
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        when(technicianStatusRepository.findById(anyLong())).thenReturn(Optional.of(technicianStatusEntity));

        TechnicianStatusDTO dto = technicianStatusService.getById(1L);

        assertNotNull(dto);
        assertEquals(technicianStatusEntity.getId(), dto.getId());
        assertEquals(technicianStatusEntity.getStatus(),dto.getStatus());
        verify(technicianStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdNotFound() {
        when(technicianStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            technicianStatusService.getById(1L);
        });

        verify(technicianStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteByIdSuccess() throws UserNotFoundException {
        when(technicianStatusRepository.findById(anyLong())).thenReturn(Optional.of(technicianStatusEntity));
        doNothing().when(technicianStatusRepository).deleteById(anyLong());

        technicianStatusService.deleteById(1L);

        verify(technicianStatusRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNotFound() {
        when(technicianStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            technicianStatusService.deleteById(1L);
        });

        verify(technicianStatusRepository, times(1)).findById(anyLong());
        verify(technicianStatusRepository, times(0)).deleteById(anyLong());
    }
}
