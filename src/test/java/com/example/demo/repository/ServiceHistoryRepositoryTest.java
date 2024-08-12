package com.example.demo.repository;

import com.example.demo.entity.HistoryEntity;
import com.example.demo.entity.ServiceAppointmentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceHistoryRepositoryTest {

    @Mock
    private ServiceHistoryRepository historyRepository;

    private HistoryEntity history;
    private ServiceAppointmentEntity serviceAppointment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        serviceAppointment = new ServiceAppointmentEntity();
        serviceAppointment.setId(1L);
        // Set other properties if needed

        history = new HistoryEntity();
        history.setId(1L);
        history.setServiceAppointmentEntity(serviceAppointment);
    }

    @Test
    public void testFindByIdSuccess() {
        when(historyRepository.findById(1L)).thenReturn(Optional.of(history));

        Optional<HistoryEntity> foundHistory = historyRepository.findById(1L);

        assertTrue(foundHistory.isPresent());
        assertEquals(1L, foundHistory.get().getId());
        assertEquals(serviceAppointment, foundHistory.get().getServiceAppointmentEntity());
    }

    @Test
    public void testFindByIdNotFound() {
        when(historyRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<HistoryEntity> foundHistory = historyRepository.findById(2L);

        assertFalse(foundHistory.isPresent());
    }

    @Test
    public void testSaveHistory() {
        when(historyRepository.save(any(HistoryEntity.class))).thenReturn(history);

        HistoryEntity savedHistory = historyRepository.save(history);

        assertNotNull(savedHistory);
        assertEquals(1L, savedHistory.getId());
        assertEquals(serviceAppointment, savedHistory.getServiceAppointmentEntity());
    }

    @Test
    public void testDeleteHistory() {
        doNothing().when(historyRepository).delete(history);

        historyRepository.delete(history);

        verify(historyRepository, times(1)).delete(history);
    }
}
