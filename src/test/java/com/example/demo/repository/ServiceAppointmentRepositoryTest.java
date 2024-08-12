package com.example.demo.repository;

import com.example.demo.entity.ServiceAppointmentEntity;
import com.example.demo.entity.TechnicianStatusEntity;
import com.example.demo.entity.ServiceTypeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceAppointmentRepositoryTest {

    @Mock
    private ServiceAppointmentRepository serviceAppointmentRepository;

    private ServiceAppointmentEntity serviceAppointment;
    private TechnicianStatusEntity technicianStatus;
    private ServiceTypeEntity serviceType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        technicianStatus = new TechnicianStatusEntity();
        technicianStatus.setId(1L);

        serviceType = new ServiceTypeEntity();
        serviceType.setId(1L);

        serviceAppointment = new ServiceAppointmentEntity();
        serviceAppointment.setId(1L);
        serviceAppointment.setAppDate("2024-07-20");
        serviceAppointment.setTechnicianStatusEntity(technicianStatus);
        serviceAppointment.setServiceTypeEntity(serviceType);
    }

    @Test
    public void testFindByIdSuccess() {
        when(serviceAppointmentRepository.findById(1L)).thenReturn(Optional.of(serviceAppointment));

        Optional<ServiceAppointmentEntity> foundAppointment = serviceAppointmentRepository.findById(1L);

        assertTrue(foundAppointment.isPresent());
        assertEquals(1L, foundAppointment.get().getId());
        assertEquals("2024-07-20", foundAppointment.get().getAppDate());
        assertEquals(technicianStatus, foundAppointment.get().getTechnicianStatusEntity());
        assertEquals(serviceType, foundAppointment.get().getServiceTypeEntity());
    }

    @Test
    public void testFindByIdNotFound() {
        when(serviceAppointmentRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<ServiceAppointmentEntity> foundAppointment = serviceAppointmentRepository.findById(2L);

        assertFalse(foundAppointment.isPresent());
    }

    @Test
    public void testSaveServiceAppointment() {
        when(serviceAppointmentRepository.save(any(ServiceAppointmentEntity.class))).thenReturn(serviceAppointment);

        ServiceAppointmentEntity savedAppointment = serviceAppointmentRepository.save(serviceAppointment);

        assertNotNull(savedAppointment);
        assertEquals(1L, savedAppointment.getId());
        assertEquals("2024-07-20", savedAppointment.getAppDate());
        assertEquals(technicianStatus, savedAppointment.getTechnicianStatusEntity());
        assertEquals(serviceType, savedAppointment.getServiceTypeEntity());
    }

    @Test
    public void testDeleteServiceAppointment() {
        doNothing().when(serviceAppointmentRepository).delete(serviceAppointment);

        serviceAppointmentRepository.delete(serviceAppointment);

        verify(serviceAppointmentRepository, times(1)).delete(serviceAppointment);
    }
}
