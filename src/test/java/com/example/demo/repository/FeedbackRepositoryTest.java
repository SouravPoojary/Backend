package com.example.demo.repository;

import com.example.demo.entity.FeedbackEntity;
import com.example.demo.entity.ServiceTypeEntity;
import com.example.demo.entity.TechnicianDetailsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FeedbackRepositoryTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    private FeedbackEntity feedback;
    private ServiceTypeEntity serviceType;
    private TechnicianDetailsEntity technicianDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        serviceType = new ServiceTypeEntity();
        serviceType.setId(1L);
        serviceType.setServicename("Electrical");

        technicianDetails = new TechnicianDetailsEntity();
        technicianDetails.setId(1L);
        technicianDetails.setTec_name("John Doe");

        feedback = new FeedbackEntity();
        feedback.setId(1L);
        feedback.setComment("Great service!");
        feedback.setRating("5");
        feedback.setServiceTypeEntity(serviceType);
        feedback.setTechnicianDetailsEntity(technicianDetails);
    }

    @Test
    public void testFindByIdSuccess() {
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(feedback));

        Optional<FeedbackEntity> foundFeedback = feedbackRepository.findById(1L);

        assertTrue(foundFeedback.isPresent());
        assertEquals("Great service!", foundFeedback.get().getComment());
        assertEquals("5", foundFeedback.get().getRating());
        assertEquals(serviceType, foundFeedback.get().getServiceTypeEntity());
        assertEquals(technicianDetails, foundFeedback.get().getTechnicianDetailsEntity());
    }

    @Test
    public void testFindByIdNotFound() {
        when(feedbackRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<FeedbackEntity> foundFeedback = feedbackRepository.findById(2L);

        assertFalse(foundFeedback.isPresent());
    }

    @Test
    public void testSaveFeedback() {
        when(feedbackRepository.save(any(FeedbackEntity.class))).thenReturn(feedback);

        FeedbackEntity savedFeedback = feedbackRepository.save(feedback);

        assertNotNull(savedFeedback);
        assertEquals("Great service!", savedFeedback.getComment());
        assertEquals("5", savedFeedback.getRating());
        assertEquals(serviceType, savedFeedback.getServiceTypeEntity());
        assertEquals(technicianDetails, savedFeedback.getTechnicianDetailsEntity());
    }

    @Test
    public void testDeleteFeedback() {
        doNothing().when(feedbackRepository).delete(feedback);

        feedbackRepository.delete(feedback);

        verify(feedbackRepository, times(1)).delete(feedback);
    }
}
