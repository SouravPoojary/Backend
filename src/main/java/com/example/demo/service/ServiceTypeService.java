package com.example.demo.service;

import com.example.demo.dto.ServiceTypeOptionDTO;
import com.example.demo.entity.ServiceTypeEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ServiceTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceTypeService {
    @Autowired
    private ServiceTypeRepository typeRepository;
    public ServiceTypeEntity DtoToEntity(ServiceTypeOptionDTO dto){
        ServiceTypeEntity entity=new ServiceTypeEntity();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }


    public ServiceTypeOptionDTO EntityToDto(ServiceTypeEntity entity){
        ServiceTypeOptionDTO dto=new ServiceTypeOptionDTO();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public List<ServiceTypeOptionDTO> getAll(){
        List<ServiceTypeOptionDTO> dtos=new ArrayList<>();
        for(ServiceTypeEntity entity: typeRepository.findAll()){
            dtos.add(EntityToDto(entity));
        }
        return dtos;
    }

    public ServiceTypeOptionDTO getById(long id) throws UserNotFoundException {
        ServiceTypeEntity entity = typeRepository.findById(id).orElse(null);
        if (entity != null) {
            return EntityToDto(entity);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }
    public ServiceTypeOptionDTO saveDetails(ServiceTypeOptionDTO dto){
        ServiceTypeEntity entity=DtoToEntity(dto);
        return EntityToDto(typeRepository.save(entity));
    }

    public  void deleteById(long id) throws UserNotFoundException{
        ServiceTypeEntity entity= typeRepository.findById(id).orElse(null);
        if(entity!=null){
            typeRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }

}

