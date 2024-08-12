package com.example.demo.repository;

import com.example.demo.entity.TechnicianDetailsEntity;
import com.example.demo.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TechnicianDetailsRepositoryTest {

    @Mock
    private TechnicianDetailsRepository technicianDetailsRepository;

    private TechnicianDetailsEntity technicianDetails;
    private Address address;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        address = new Address();
        address.setCity("Udupi");
        address.setStreet("Church Street");
        address.setPincode("656565");

        technicianDetails = new TechnicianDetailsEntity();
        technicianDetails.setId(1L);
        technicianDetails.setTec_name("John Doe");
        technicianDetails.setTec_mobno("9855564881");
        technicianDetails.setAddress(address);
    }

    @Test
    public void testFindByIdSuccess() {
        when(technicianDetailsRepository.findById(1L)).thenReturn(Optional.of(technicianDetails));

        Optional<TechnicianDetailsEntity> foundTechnician = technicianDetailsRepository.findById(1L);

        assertTrue(foundTechnician.isPresent());
        assertEquals(1L, foundTechnician.get().getId());
        assertEquals("John Doe", foundTechnician.get().getTec_name());
        assertEquals("9855564881", foundTechnician.get().getTec_mobno());
        assertEquals(address, foundTechnician.get().getAddress());
    }

    @Test
    public void testFindByIdNotFound() {
        when(technicianDetailsRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<TechnicianDetailsEntity> foundTechnician = technicianDetailsRepository.findById(2L);

        assertFalse(foundTechnician.isPresent());
    }

    @Test
    public void testSaveTechnicianDetails() {
        when(technicianDetailsRepository.save(any(TechnicianDetailsEntity.class))).thenReturn(technicianDetails);

        TechnicianDetailsEntity savedTechnician = technicianDetailsRepository.save(technicianDetails);

        assertNotNull(savedTechnician);
        assertEquals(1L, savedTechnician.getId());
        assertEquals("John Doe", savedTechnician.getTec_name());
        assertEquals("9855564881", savedTechnician.getTec_mobno());
        assertEquals(address, savedTechnician.getAddress());
    }

    @Test
    public void testDeleteTechnicianDetails() {
        doNothing().when(technicianDetailsRepository).delete(technicianDetails);

        technicianDetailsRepository.delete(technicianDetails);

        verify(technicianDetailsRepository, times(1)).delete(technicianDetails);
    }
}
