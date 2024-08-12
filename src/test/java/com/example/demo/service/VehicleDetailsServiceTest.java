package com.example.demo.service;

import com.example.demo.dto.VehicleDetailsDTO;
import com.example.demo.entity.VehicleDetailsEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.VehicleDetailsRepository;
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

public class VehicleDetailsServiceTest {

    @Mock
    private VehicleDetailsRepository vehicleDetailsRepository;

    @InjectMocks
    private VehicleDetailsService vehicleDetailsService;

    private VehicleDetailsEntity vehicleDetailsEntity;
    private VehicleDetailsDTO vehicleDetailsDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vehicleDetailsEntity = new VehicleDetailsEntity();
        vehicleDetailsEntity.setId(1L);
        vehicleDetailsEntity.setCompname("Toyota");
        vehicleDetailsEntity.setVehicleno("123ABC");

        vehicleDetailsDTO = new VehicleDetailsDTO();
        vehicleDetailsDTO.setId(1L);
        vehicleDetailsDTO.setCompname("Toyota");
        vehicleDetailsDTO.setVehicleno("123ABC");
    }

    @Test
    void DtoToEntity() {
        VehicleDetailsDTO dto = new VehicleDetailsDTO();
        dto.setId(1L);
        dto.setCompname("Toyota");
        dto.setVehicleno("123ABC");

        VehicleDetailsEntity entity = vehicleDetailsService.DtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getCompname(), entity.getCompname());
        assertEquals(dto.getVehicleno(), entity.getVehicleno());
    }

    @Test
    void EntityToDto() {
        VehicleDetailsEntity entity = new VehicleDetailsEntity();
        entity.setId(1L);
        entity.setCompname("Toyota");
        entity.setVehicleno("123ABC");

        VehicleDetailsDTO dto = vehicleDetailsService.EntityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getCompname(), dto.getCompname());
        assertEquals(entity.getVehicleno(), dto.getVehicleno());
    }

    @Test
    void saveDetailsSuccess() {
        BeanUtils.copyProperties(vehicleDetailsDTO, vehicleDetailsEntity);
        when(vehicleDetailsRepository.save(any(VehicleDetailsEntity.class))).thenReturn(vehicleDetailsEntity);

        VehicleDetailsDTO savedDto = vehicleDetailsService.saveDetails(vehicleDetailsDTO);

        assertNotNull(savedDto);
        assertEquals(vehicleDetailsDTO.getId(), savedDto.getId());
        verify(vehicleDetailsRepository, times(1)).save(any(VehicleDetailsEntity.class));
    }

    @Test
    void getAllSuccess() {
        List<VehicleDetailsEntity> vehicleList = new ArrayList<>();
        vehicleList.add(vehicleDetailsEntity);

        when(vehicleDetailsRepository.findAll()).thenReturn(vehicleList);

        List<VehicleDetailsDTO> dtoList = vehicleDetailsService.getAll();

        assertNotNull(dtoList);
        assertFalse(dtoList.isEmpty());
        assertEquals(1, dtoList.size());
        assertEquals(vehicleDetailsEntity.getId(), dtoList.get(0).getId());
        assertEquals(vehicleDetailsEntity.getCompname(), dtoList.get(0).getCompname());
        assertEquals(vehicleDetailsEntity.getVehicleno(), dtoList.get(0).getVehicleno());
        verify(vehicleDetailsRepository, times(1)).findAll();
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        when(vehicleDetailsRepository.findById(anyLong())).thenReturn(Optional.of(vehicleDetailsEntity));

        VehicleDetailsDTO dto = vehicleDetailsService.getById(1L);

        assertNotNull(dto);
        assertEquals(vehicleDetailsEntity.getId(), dto.getId());
        assertEquals(vehicleDetailsEntity.getCompname(), dto.getCompname());
        assertEquals(vehicleDetailsEntity.getVehicleno(), dto.getVehicleno());
        verify(vehicleDetailsRepository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdNotFound() {
        when(vehicleDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            vehicleDetailsService.getById(1L);
        });

        verify(vehicleDetailsRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteByIdSuccess() throws UserNotFoundException {
        when(vehicleDetailsRepository.findById(anyLong())).thenReturn(Optional.of(vehicleDetailsEntity));
        doNothing().when(vehicleDetailsRepository).deleteById(anyLong());

        vehicleDetailsService.deleteById(1L);

        verify(vehicleDetailsRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNotFound() {
        when(vehicleDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            vehicleDetailsService.deleteById(1L);
        });

        verify(vehicleDetailsRepository, times(1)).findById(anyLong());
        verify(vehicleDetailsRepository, times(0)).deleteById(anyLong());
    }
}
