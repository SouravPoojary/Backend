package com.example.demo.service;

import com.example.demo.dto.ServiceTypeOptionDTO;
import com.example.demo.entity.ServiceTypeEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ServiceTypeRepository;
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

public class ServiceTypeServiceTest {

    @Mock
    private ServiceTypeRepository typeRepository;

    @InjectMocks
    private ServiceTypeService serviceTypeService;

    private ServiceTypeEntity serviceTypeEntity;
    private ServiceTypeOptionDTO serviceTypeOptionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        serviceTypeEntity = new ServiceTypeEntity();
        serviceTypeEntity.setId(1L);
        serviceTypeEntity.setServicename("Oil Change");
        serviceTypeEntity.setServicedetails("Complete oil change");
        serviceTypeEntity.setPrice("29.99");

        serviceTypeOptionDTO = new ServiceTypeOptionDTO();
        serviceTypeOptionDTO.setId(1L);
        serviceTypeOptionDTO.setServicename("Oil Change");
        serviceTypeOptionDTO.setServicedetails("Complete oil change");
        serviceTypeOptionDTO.setPrice("29.99");
    }

    @Test
    void DtoToEntity() {
        ServiceTypeOptionDTO dto = new ServiceTypeOptionDTO();
        dto.setId(1L);
        dto.setServicename("Oil Change");
        dto.setServicedetails("Complete oil change");
        dto.setPrice("29.99");

        ServiceTypeEntity entity = serviceTypeService.DtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getServicename(), entity.getServicename());
        assertEquals(dto.getServicedetails(), entity.getServicedetails());
        assertEquals(dto.getPrice(), entity.getPrice());
    }

    @Test
    void EntityToDto() {
        ServiceTypeEntity entity = new ServiceTypeEntity();
        entity.setId(1L);
        entity.setServicename("Oil Change");
        entity.setServicedetails("Complete oil change");
        entity.setPrice("29.99");

        ServiceTypeOptionDTO dto = serviceTypeService.EntityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getServicename(), dto.getServicename());
        assertEquals(entity.getServicedetails(), dto.getServicedetails());
        assertEquals(entity.getPrice(), dto.getPrice());
    }

    @Test
    void saveDetailsSuccess() {
        BeanUtils.copyProperties(serviceTypeOptionDTO, serviceTypeEntity);
        when(typeRepository.save(any(ServiceTypeEntity.class))).thenReturn(serviceTypeEntity);

        ServiceTypeOptionDTO savedDto = serviceTypeService.saveDetails(serviceTypeOptionDTO);

        assertNotNull(savedDto);
        assertEquals(serviceTypeOptionDTO.getId(), savedDto.getId());
        verify(typeRepository, times(1)).save(any(ServiceTypeEntity.class));
    }

    @Test
    void getAllSuccess() {
        List<ServiceTypeEntity> serviceTypeList = new ArrayList<>();
        serviceTypeList.add(serviceTypeEntity);

        when(typeRepository.findAll()).thenReturn(serviceTypeList);

        List<ServiceTypeOptionDTO> dtoList = serviceTypeService.getAll();

        assertNotNull(dtoList);
        assertFalse(dtoList.isEmpty());
        assertEquals(1, dtoList.size());
        assertEquals(serviceTypeEntity.getId(), serviceTypeOptionDTO.getId());
        assertEquals(serviceTypeEntity.getServicename(), serviceTypeOptionDTO.getServicename());
        assertEquals(serviceTypeEntity.getServicedetails(), serviceTypeOptionDTO.getServicedetails());
        assertEquals(serviceTypeEntity.getPrice(), serviceTypeOptionDTO.getPrice());
        verify(typeRepository, times(1)).findAll();
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        when(typeRepository.findById(anyLong())).thenReturn(Optional.of(serviceTypeEntity));

        ServiceTypeOptionDTO dto = serviceTypeService.getById(1L);

        assertNotNull(dto);
        assertEquals(serviceTypeEntity.getId(), dto.getId());
        assertEquals(serviceTypeEntity.getServicename(), dto.getServicename());
        assertEquals(serviceTypeEntity.getServicedetails(), dto.getServicedetails());
        assertEquals(serviceTypeEntity.getPrice(), dto.getPrice());
        verify(typeRepository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdNotFound() {
        when(typeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            serviceTypeService.getById(1L);
        });

        verify(typeRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteByIdSuccess() throws UserNotFoundException {
        when(typeRepository.findById(anyLong())).thenReturn(Optional.of(serviceTypeEntity));
        doNothing().when(typeRepository).deleteById(anyLong());

        serviceTypeService.deleteById(1L);

        verify(typeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNotFound() {
        when(typeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            serviceTypeService.deleteById(1L);
        });

        verify(typeRepository, times(1)).findById(anyLong());
        verify(typeRepository, times(0)).deleteById(anyLong());
    }
}
