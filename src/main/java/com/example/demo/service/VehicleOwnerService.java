package com.example.demo.service;

import com.example.demo.dto.VehicleOwnerDTO;
import com.example.demo.entity.VehicleOwnerEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.VehicleOwnerRepository;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class VehicleOwnerService {
    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;

    public VehicleOwnerEntity DtoToEntity(VehicleOwnerDTO dto){
        VehicleOwnerEntity entity=new VehicleOwnerEntity();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }


    public VehicleOwnerDTO EntityToDto(VehicleOwnerEntity entity){
        VehicleOwnerDTO dto=new VehicleOwnerDTO();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public List<VehicleOwnerDTO> getAll(){
        List<VehicleOwnerDTO> dtos=new ArrayList<>();
        for(VehicleOwnerEntity entity: vehicleOwnerRepository.findAll()){
            dtos.add(EntityToDto(entity));
        }
        return dtos;
    }

    public VehicleOwnerDTO getById(long id) throws UserNotFoundException {
        VehicleOwnerEntity entity = vehicleOwnerRepository.findById(id).orElse(null);
        if (entity != null) {
            return EntityToDto(entity);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }
    public VehicleOwnerDTO saveDetails(VehicleOwnerDTO dto){
        VehicleOwnerEntity entity=DtoToEntity(dto);
        return EntityToDto(vehicleOwnerRepository.save(entity));
    }

    public  void deleteById(long id) throws UserNotFoundException{
        VehicleOwnerEntity entity= vehicleOwnerRepository.findById(id).orElse(null);
        if(entity!=null){
            vehicleOwnerRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }



}
