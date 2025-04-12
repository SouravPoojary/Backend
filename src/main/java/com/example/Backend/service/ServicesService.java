package com.example.Backend.service;

import com.example.Backend.dto.ServicesDto;

import com.example.Backend.entity.Services;
import com.example.Backend.entity.User;
import com.example.Backend.exception.UserNotFoundException;

import com.example.Backend.repository.ServicesRepository;
import com.example.Backend.repository.UserRepository;
import com.example.Backend.resource.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesService {
    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private UserRepository userRepository;

    public Services dtoToEntity(ServicesDto dto){
        Services entity=new Services();
//        entity.setId(dto.getServiceCenterId().getId());
entity.setId(dto.getId());
        entity.setServiceName(dto.getServiceName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setDeliveryTime(dto.getDeliveryTime());
        entity.setMinPrice(dto.getMinPrice());

        User sCenter=userRepository.findById(dto.getServiceCenterId().getId()).
                orElseThrow(()->new RuntimeException("Service Center not found with id:"+dto.getServiceCenterId()));
        entity.setServiceCenterId(sCenter);


        return entity;
    }

    public ServicesDto entityToDto (Services entity){
        ServicesDto dto=new ServicesDto();
        dto.setId(entity.getId());
        dto.setServiceCenterId(entity.getServiceCenterId());
        dto.setServiceName(entity.getServiceName());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        dto.setDeliveryTime(entity.getDeliveryTime());
        dto.setMinPrice(entity.getMinPrice());
//        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public ServicesDto create(ServicesDto dto){
        Services entity=servicesRepository.save(dtoToEntity(dto));
        return (entityToDto(entity));
    }

    public List<ServicesDto> getAll(){
        List<ServicesDto> dtos=new ArrayList<>();
        for( Services entity: servicesRepository.findAll()){
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    public ServicesDto getById(Long id) throws UserNotFoundException {
        Services entity=servicesRepository.findById(id).orElse(null);
        if(entity!=null)
            return entityToDto(entity);
        else
            throw new UserNotFoundException("service not found");
    }

    public void deleteById(Long id,Long userId,Role role) throws UserNotFoundException {
        Services entity=servicesRepository.findById(id).orElseThrow(()->new RuntimeException("Service not found"));
//        if(entity!=null)
        if (role!=Role.ADMIN && !entity.getServiceCenterId().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own services!");
        }
            servicesRepository.deleteById(id);

    }
}
