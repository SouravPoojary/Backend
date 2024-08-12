package com.example.demo.repository;

import com.example.demo.entity.TechnicianDetailsEntity;
import com.example.demo.entity.TechnicianStatusEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TechnicianStatusRepositoryTest {

    @Mock
    private TechnicianStatusRepository technicianStatusRepository;

    private TechnicianStatusEntity technicianStatus;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        technicianStatus = new TechnicianStatusEntity();
        technicianStatus.setId(1L);
        technicianStatus.setStatus("Active");


    }

    @Test
    public void testFindByIdSuccess() {
        when(technicianStatusRepository.findById(1L)).thenReturn(Optional.of(technicianStatus));
        Optional<TechnicianStatusEntity> foundTechnicianStatus = technicianStatusRepository.findById(1L);

        assertTrue(foundTechnicianStatus.isPresent());
        assertEquals(technicianStatus.getId(), foundTechnicianStatus.get().getId());
    }

    @Test
    public void testFindByIdNotFound() {
        when(technicianStatusRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<TechnicianStatusEntity> foundTechnicianStatus = technicianStatusRepository.findById(2L);

        assertFalse(foundTechnicianStatus.isPresent());
    }

    @Test
    public void testSaveTechnicianStatus() {
        when(technicianStatusRepository.save(any(TechnicianStatusEntity.class))).thenReturn(technicianStatus);
        TechnicianStatusEntity savedTechnicianStatus = technicianStatusRepository.save(technicianStatus);

        assertNotNull(savedTechnicianStatus);
        assertEquals(technicianStatus.getId(), savedTechnicianStatus.getId());
    }

    @Test
    public void testDeleteTechnicianStatus() {
        doNothing().when(technicianStatusRepository).delete(technicianStatus);
        technicianStatusRepository.delete(technicianStatus);
        verify(technicianStatusRepository, times(1)).delete(technicianStatus);
    }
}
