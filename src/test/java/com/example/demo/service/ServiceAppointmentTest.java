package com.example.demo.service;

import com.example.demo.dto.ServiceAppointmentDTO;
import com.example.demo.entity.ServiceAppointmentEntity;
import com.example.demo.entity.ServiceTypeEntity;
import com.example.demo.entity.TechnicianStatusEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ServiceAppointmentRepository;
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

public class ServiceAppointmentTest {

    @Mock
    private ServiceAppointmentRepository serviceAppointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private ServiceAppointmentEntity serviceAppointmentEntity;
    private ServiceAppointmentDTO serviceAppointmentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        TechnicianStatusEntity technicianStatusEntity = new TechnicianStatusEntity();
        technicianStatusEntity.setId(1L);
        technicianStatusEntity.setStatus("Available");

        ServiceTypeEntity serviceTypeEntity = new ServiceTypeEntity();
        serviceTypeEntity.setId(1L);
        serviceTypeEntity.setServicename("Oil Change");

        serviceAppointmentEntity = new ServiceAppointmentEntity();
        serviceAppointmentEntity.setId(1L);
        serviceAppointmentEntity.setAppDate("2023-07-20");
        serviceAppointmentEntity.setTechnicianStatusEntity(technicianStatusEntity);
        serviceAppointmentEntity.setServiceTypeEntity(serviceTypeEntity);

        serviceAppointmentDTO = new ServiceAppointmentDTO();
        serviceAppointmentDTO.setId(1L);
        serviceAppointmentDTO.setAppDate("2023-07-20");
        serviceAppointmentDTO.setTechnicianStatusEntity(technicianStatusEntity);
        serviceAppointmentDTO.setServiceTypeEntity(serviceTypeEntity);
    }

    @Test
    void DtoToEntity() {
        ServiceAppointmentDTO dto = new ServiceAppointmentDTO();
        dto.setId(1L);
        dto.setAppDate("2023-07-20");

        ServiceAppointmentEntity entity = appointmentService.DtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getAppDate(), entity.getAppDate());
    }

    @Test
    void EntityToDto() {
        ServiceAppointmentEntity entity = new ServiceAppointmentEntity();
        entity.setId(1L);
        entity.setAppDate("2023-07-20");

        ServiceAppointmentDTO dto = appointmentService.EntityToDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getAppDate(), dto.getAppDate());
    }

    @Test
    void saveDetailsSuccess() {
        BeanUtils.copyProperties(serviceAppointmentDTO, serviceAppointmentEntity);
        when(serviceAppointmentRepository.save(any(ServiceAppointmentEntity.class))).thenReturn(serviceAppointmentEntity);

        ServiceAppointmentDTO savedDto = appointmentService.saveDetails(serviceAppointmentDTO);

        assertNotNull(savedDto);
        assertEquals(serviceAppointmentDTO.getId(), savedDto.getId());
        verify(serviceAppointmentRepository, times(1)).save(any(ServiceAppointmentEntity.class));
    }

    @Test
    void getAllSuccess() {
        List<ServiceAppointmentEntity> serviceAppointmentList = new ArrayList<>();
        serviceAppointmentList.add(serviceAppointmentEntity);

        when(serviceAppointmentRepository.findAll()).thenReturn(serviceAppointmentList);

        List<ServiceAppointmentDTO> dtoList = appointmentService.getAll();

        assertNotNull(dtoList);
        assertFalse(dtoList.isEmpty());
        assertEquals(1, dtoList.size());
        assertEquals(serviceAppointmentEntity.getId(), serviceAppointmentDTO.getId());
        assertEquals(serviceAppointmentEntity.getAppDate(), serviceAppointmentDTO.getAppDate());
        verify(serviceAppointmentRepository, times(1)).findAll();
    }

    @Test
    void getByIdSuccess() throws UserNotFoundException {
        when(serviceAppointmentRepository.findById(anyLong())).thenReturn(Optional.of(serviceAppointmentEntity));

        ServiceAppointmentDTO dto = appointmentService.getById(1L);

        assertNotNull(dto);
        assertEquals(serviceAppointmentEntity.getId(), dto.getId());
        assertEquals(serviceAppointmentEntity.getAppDate(), dto.getAppDate());
        verify(serviceAppointmentRepository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdNotFound() {
        when(serviceAppointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            appointmentService.getById(1L);
        });

        verify(serviceAppointmentRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteByIdSuccess() throws UserNotFoundException {
        when(serviceAppointmentRepository.findById(anyLong())).thenReturn(Optional.of(serviceAppointmentEntity));
        doNothing().when(serviceAppointmentRepository).deleteById(anyLong());

        appointmentService.deleteById(1L);

        verify(serviceAppointmentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteByIdNotFound() {
        when(serviceAppointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            appointmentService.deleteById(1L);
        });

        verify(serviceAppointmentRepository, times(1)).findById(anyLong());
        verify(serviceAppointmentRepository, times(0)).deleteById(anyLong());
    }
}
