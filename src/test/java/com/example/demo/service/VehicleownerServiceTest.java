package com.example.demo.service;

import com.example.demo.dto.VehicleOwnerDTO;
import com.example.demo.entity.VehicleOwnerEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.VehicleOwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VehicleownerServiceTest {
    @Mock
    private VehicleOwnerRepository vehicleOwnerRepository;

    @InjectMocks
    private VehicleOwnerService vehicleOwnerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess(){
        VehicleOwnerDTO vehicleOwnerDTO=new VehicleOwnerDTO();
        vehicleOwnerDTO.setId(1L);

        VehicleOwnerEntity vehicleOwnerEntity=new VehicleOwnerEntity();
        vehicleOwnerEntity.setId(1L);

        when(vehicleOwnerRepository.save(any(VehicleOwnerEntity.class))).thenReturn(vehicleOwnerEntity);

        VehicleOwnerDTO result=vehicleOwnerService.saveDetails(vehicleOwnerDTO);

        assertEquals(vehicleOwnerDTO.getId(),result.getId());
        verify(vehicleOwnerRepository,times(1)).save(any(VehicleOwnerEntity.class));
    }

    @Test
    public  void testGetAllSuccess(){
        List<VehicleOwnerEntity> vehicleOwnerEntities=new ArrayList<>();
        VehicleOwnerEntity vehicleOwner=new VehicleOwnerEntity();
        vehicleOwner.setId(1L);
        VehicleOwnerEntity vehicleOwner1=new VehicleOwnerEntity();
        vehicleOwner1.setId(2L);
        vehicleOwnerEntities.add(vehicleOwner);
        vehicleOwnerEntities.add(vehicleOwner1);

        when(vehicleOwnerRepository.findAll()).thenReturn(vehicleOwnerEntities);

        List<VehicleOwnerDTO> result=vehicleOwnerService.getAll();
        assertEquals(2,result.size());
        verify(vehicleOwnerRepository,times(1)).findAll();
    }

    @Test
    public void testDeleteSuccess() throws UserNotFoundException{
        Long id=1L;
        VehicleOwnerEntity vehicleOwner=new VehicleOwnerEntity();
        vehicleOwner.setId(id);

        when(vehicleOwnerRepository.findById(id)).thenReturn(Optional.of(vehicleOwner));
        doNothing().when(vehicleOwnerRepository).deleteById(id);

        vehicleOwnerService.deleteById(id);

        verify(vehicleOwnerRepository,times(1)).findById(id);
        verify(vehicleOwnerRepository,times(1)).deleteById(id);
    }

    @Test
    public void testDeleteNotFound(){
        Long id=1L;

        when(vehicleOwnerRepository.findById(id)).thenReturn(Optional.empty());

        UserNotFoundException exception=assertThrows(UserNotFoundException.class,()->{
            vehicleOwnerService.deleteById(id);
        });
assertEquals("Id not found",exception.getMessage());
verify(vehicleOwnerRepository,times(1)).findById(id);
verify(vehicleOwnerRepository,times(0)).deleteById(id);

    }
}
