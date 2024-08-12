package com.example.demo.repository;

import com.example.demo.entity.ServiceTypeEntity;
import com.example.demo.entity.VehicleDetailsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceTypeRepositoryTest {

    @Mock
    private ServiceTypeRepository serviceTypeRepository;

    private ServiceTypeEntity serviceType;
    private VehicleDetailsEntity vehicleDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        vehicleDetails = new VehicleDetailsEntity();
        vehicleDetails.setId(1L);

        serviceType = new ServiceTypeEntity();
        serviceType.setId(1L);
        serviceType.setServicename("Oil Change");
        serviceType.setServicedetails("Change the engine oil");
        serviceType.setPrice("300Rs");
        serviceType.setVehicleDetailsEntity(vehicleDetails);
    }

    @Test
    public void testFindByIdSuccess() {
        when(serviceTypeRepository.findById(1L)).thenReturn(Optional.of(serviceType));

        Optional<ServiceTypeEntity> foundServiceType = serviceTypeRepository.findById(1L);

        assertTrue(foundServiceType.isPresent());
        assertEquals(1L, foundServiceType.get().getId());
        assertEquals("Oil Change", foundServiceType.get().getServicename());
        assertEquals("Change the engine oil", foundServiceType.get().getServicedetails());
        assertEquals("300Rs", foundServiceType.get().getPrice());
        assertEquals(vehicleDetails, foundServiceType.get().getVehicleDetailsEntity());
    }

    @Test
    public void testFindByIdNotFound() {
        when(serviceTypeRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<ServiceTypeEntity> foundServiceType = serviceTypeRepository.findById(2L);

        assertFalse(foundServiceType.isPresent());
    }

    @Test
    public void testSaveServiceType() {
        when(serviceTypeRepository.save(any(ServiceTypeEntity.class))).thenReturn(serviceType);

        ServiceTypeEntity savedServiceType = serviceTypeRepository.save(serviceType);

        assertNotNull(savedServiceType);
        assertEquals(1L, savedServiceType.getId());
        assertEquals("Oil Change", savedServiceType.getServicename());
        assertEquals("Change the engine oil", savedServiceType.getServicedetails());
        assertEquals("300Rs", savedServiceType.getPrice());
        assertEquals(vehicleDetails, savedServiceType.getVehicleDetailsEntity());
    }

    @Test
    public void testDeleteServiceType() {
        doNothing().when(serviceTypeRepository).delete(serviceType);

        serviceTypeRepository.delete(serviceType);

        verify(serviceTypeRepository, times(1)).delete(serviceType);
    }
}
