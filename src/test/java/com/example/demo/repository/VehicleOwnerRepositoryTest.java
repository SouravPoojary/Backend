package com.example.demo.repository;

import com.example.demo.Address;
import com.example.demo.entity.VehicleOwnerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VehicleOwnerRepositoryTest {

    @Mock
    private VehicleOwnerRepository vehicleOwnerRepository;

    private Address address;
    private VehicleOwnerEntity vehicleOwner;

    @BeforeEach
    public void setUP(){
        MockitoAnnotations.openMocks(this);

        address=new Address();
        address.setPincode("656565");
        address.setCity("Udupi");
        address.setStreet("Church Street");

        vehicleOwner=new VehicleOwnerEntity();
        vehicleOwner.setOwner_email("sourav@gmail.com");
        vehicleOwner.setOwner_name("sourav");
        vehicleOwner.setOwner_mobno("9874563210");

    }

    @Test
    public void testFindByIdSuccess(){
        when(vehicleOwnerRepository.findById(1L)).thenReturn(Optional.of(vehicleOwner));
        Optional<VehicleOwnerEntity> foundOwner=vehicleOwnerRepository.findById(1L);

        assertTrue(foundOwner.isPresent());
        assertEquals(vehicleOwner.getId(),foundOwner.get().getId());
    }

    @Test
    public  void  testFindByIdNotFound(){
        when(vehicleOwnerRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<VehicleOwnerEntity> foundOwner=vehicleOwnerRepository.findById(2L);
        assertFalse(foundOwner.isPresent());
    }

    @Test
    public void testSaveVehicleOwner(){
        when(vehicleOwnerRepository.save(any(VehicleOwnerEntity.class))).thenReturn(vehicleOwner);
        VehicleOwnerEntity vehicleOwnerEntity=vehicleOwnerRepository.save(vehicleOwner);

        assertNotNull(vehicleOwnerEntity);
        assertEquals(vehicleOwner.getId(),vehicleOwnerEntity.getId());
    }

    @Test
    public void testdeleteVehicleOwner(){
        doNothing().when(vehicleOwnerRepository).delete(vehicleOwner);
        vehicleOwnerRepository.delete(vehicleOwner);
        verify(vehicleOwnerRepository,times(1)).delete(vehicleOwner);
    }

}
