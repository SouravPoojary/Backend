package com.example.demo.repository;

import com.example.demo.entity.VehicleDetailsEntity;
import com.example.demo.entity.VehicleOwnerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VehicleDetailsRepositoryTest {

    @Mock
    private VehicleDetailsRepository vehicleDetailsRepository;

    private VehicleDetailsEntity vehicleDetails;
    private VehicleOwnerEntity vehicleOwner;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        vehicleOwner = new VehicleOwnerEntity();
        vehicleOwner.setId(1L);
        vehicleOwner.setOwner_name("Sourav");
        vehicleOwner.setOwner_mobno("900812169");
        vehicleOwner.setOwner_email("sourav@gmail.com");

        vehicleDetails = new VehicleDetailsEntity();
        vehicleDetails.setId(1L);
        vehicleDetails.setCompname("Toyota");
        vehicleDetails.setVehicleno("KA-01-AB-1234");
        vehicleDetails.setVehicleOwnerEntity(vehicleOwner);
    }

    @Test
    public void testFindByIdSuccess() {
        when(vehicleDetailsRepository.findById(1L)).thenReturn(Optional.of(vehicleDetails));

        Optional<VehicleDetailsEntity> foundVehicleDetails = vehicleDetailsRepository.findById(1L);

        assertTrue(foundVehicleDetails.isPresent());
        assertEquals(1L, foundVehicleDetails.get().getId());
        assertEquals("Toyota", foundVehicleDetails.get().getCompname());
        assertEquals("KA-01-AB-1234", foundVehicleDetails.get().getVehicleno());
        assertEquals(vehicleOwner, foundVehicleDetails.get().getVehicleOwnerEntity());
    }

    @Test
    public void testFindByIdNotFound() {
        when(vehicleDetailsRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<VehicleDetailsEntity> foundVehicleDetails = vehicleDetailsRepository.findById(2L);

        assertFalse(foundVehicleDetails.isPresent());
    }

    @Test
    public void testSaveVehicleDetails() {
        when(vehicleDetailsRepository.save(any(VehicleDetailsEntity.class))).thenReturn(vehicleDetails);

        VehicleDetailsEntity savedVehicleDetails = vehicleDetailsRepository.save(vehicleDetails);

        assertNotNull(savedVehicleDetails);
        assertEquals(1L, savedVehicleDetails.getId());
        assertEquals("Toyota", savedVehicleDetails.getCompname());
        assertEquals("KA-01-AB-1234", savedVehicleDetails.getVehicleno());
        assertEquals(vehicleOwner, savedVehicleDetails.getVehicleOwnerEntity());
    }

    @Test
    public void testDeleteVehicleDetails() {
        doNothing().when(vehicleDetailsRepository).delete(vehicleDetails);

        vehicleDetailsRepository.delete(vehicleDetails);

        verify(vehicleDetailsRepository, times(1)).delete(vehicleDetails);
    }
}
