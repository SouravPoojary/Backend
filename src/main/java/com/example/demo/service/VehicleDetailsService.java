package com.example.demo.service;

import com.example.demo.dto.VehicleDetailsDTO;
import com.example.demo.entity.VehicleDetailsEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.VehicleDetailsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleDetailsService {
    @Autowired
    private VehicleDetailsRepository vehicleDetailsRepository;

    public VehicleDetailsEntity DtoToEntity(VehicleDetailsDTO dto){
        VehicleDetailsEntity entity=new VehicleDetailsEntity();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }


    public VehicleDetailsDTO EntityToDto(VehicleDetailsEntity entity){
        VehicleDetailsDTO dto=new VehicleDetailsDTO();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public List<VehicleDetailsDTO> getAll(){
        List<VehicleDetailsDTO> dtos=new ArrayList<>();
        for(VehicleDetailsEntity entity: vehicleDetailsRepository.findAll()){
            dtos.add(EntityToDto(entity));
        }
        return dtos;
    }

    public VehicleDetailsDTO getById(long id) throws UserNotFoundException {
        VehicleDetailsEntity entity = vehicleDetailsRepository.findById(id).orElse(null);
        if (entity != null) {
            return EntityToDto(entity);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }
    public VehicleDetailsDTO saveDetails(VehicleDetailsDTO dto){
        VehicleDetailsEntity entity=DtoToEntity(dto);
        return EntityToDto(vehicleDetailsRepository.save(entity));
    }

    public  void deleteById(long id) throws UserNotFoundException{
        VehicleDetailsEntity entity= vehicleDetailsRepository.findById(id).orElse(null);
        if(entity!=null){
            vehicleDetailsRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }

}

