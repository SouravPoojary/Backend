package com.example.demo.service;

import com.example.demo.dto.TechnicianDTO;
import com.example.demo.entity.TechnicianDetailsEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.TechnicianDetailsRepository;
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

public class TechnicianDetailsServiceTest {

    @Mock
    private TechnicianDetailsRepository technicianDetailsRepository;

    @InjectMocks
    private TechnicianDetailsService technicianDetailsService;

    private TechnicianDetailsEntity technicianDetailsEntity;
    private TechnicianDTO technicianDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        technicianDetailsEntity = new TechnicianDetailsEntity();
        technicianDetailsEntity.setId(1L);
        technicianDetailsEntity.setTec_name("John Doe");
        technicianDetailsEntity.setTec_mobno("1234567890");

        technicianDTO = new TechnicianDTO();
        technicianDTO.setId(1L);
        technicianDTO.setTec_name("John Doe");
        technicianDTO.setTec_mobno("1234567890");
    }

    @Test
    void DtoToEntity() {
        TechnicianDTO dto = new TechnicianDTO();
        dto.setId(1L);
        dto.setTec_name("John Doe");
        dto.setTec_mobno("1234567890");

        TechnicianDetailsEntity entity = technicianDetailsService.DtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTec_name(), entity.getTec_name());
        assertEquals(dto.getTec_mobno(), entity.getTec_mobno());
    }

    @Test
    void EntityToDto() {
        TechnicianDetailsEntity entity = new TechnicianDetailsEntity();
        entity.setId(1L);
        entity.setTec_name("John Doe");
        entity.setTec_mobno("1234567890");

        TechnicianDTO dto = technicianDetailsService.EntityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTec_name(), dto.getTec_name());
        assertEquals(entity.getTec_mobno(), dto.getTec_mobno());
    }

    @Test
    void saveDetailsSuccess() {
        BeanUtils.copyProperties(technicianDTO, technicianDetailsEntity);
        when(technicianDetailsRepository.save(any(TechnicianDetailsEntity.class))).thenReturn(technicianDetailsEntity);

        TechnicianDTO savedDto = technicianDetailsService.saveDetails(technicianDTO);

        assertNotNull(savedDto);
        assertEquals(technicianDTO.getId(), savedDto.getId());
        verify(technicianDetailsRepository, times(1)).save(any(TechnicianDetailsEntity.class));
    }

    @Test
    void getAllSuccess() {
        List<TechnicianDetailsEntity> technicianList = new ArrayList<>();
        technicianList.add(technicianDetailsEntity);

        when(technicianDetailsRepository.findAll()).thenReturn(technicianList);

        List<TechnicianDTO> dtoList = technicianDetailsService.getAll();

        assertNotNull(dtoList);
        assertFalse(dtoList.isEmpty());
        assertEquals(1, dtoList.size());
        assertEquals(technicianDetailsEntity.getId(), dtoList.get(0).getId());
        assertEquals(technicianDetailsEntity.getTec_name(), dtoList.get(0).getTec_name());
        assertEquals(technicianDetailsEntity.getTec_mobno(), dtoList.get(0).getTec_mobno());
        verify(technicianDetailsRepository, times(1)).findAll();
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        when(technicianDetailsRepository.findById(anyLong())).thenReturn(Optional.of(technicianDetailsEntity));

        TechnicianDTO dto = technicianDetailsService.getById(1L);

        assertNotNull(dto);
        assertEquals(technicianDetailsEntity.getId(), dto.getId());
        assertEquals(technicianDetailsEntity.getTec_name(), dto.getTec_name());
        assertEquals(technicianDetailsEntity.getTec_mobno(), dto.getTec_mobno());
        verify(technicianDetailsRepository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdNotFound() {
        when(technicianDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            technicianDetailsService.getById(1L);
        });

        verify(technicianDetailsRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteByIdSuccess() throws UserNotFoundException {
        when(technicianDetailsRepository.findById(anyLong())).thenReturn(Optional.of(technicianDetailsEntity));
        doNothing().when(technicianDetailsRepository).deleteById(anyLong());

        technicianDetailsService.deleteById(1L);

        verify(technicianDetailsRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNotFound() {
        when(technicianDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            technicianDetailsService.deleteById(1L);
        });

        verify(technicianDetailsRepository, times(1)).findById(anyLong());
        verify(technicianDetailsRepository, times(0)).deleteById(anyLong());
    }
}
