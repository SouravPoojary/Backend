package com.example.demo.service;

import com.example.demo.dto.TechnicianDTO;
import com.example.demo.entity.TechnicianDetailsEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.TechnicianDetailsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TechnicianDetailsService {
    @Autowired
    private TechnicianDetailsRepository technicianDetailsRepository;

    public TechnicianDetailsEntity DtoToEntity(TechnicianDTO dto){
        TechnicianDetailsEntity entity=new TechnicianDetailsEntity();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }


    public TechnicianDTO EntityToDto(TechnicianDetailsEntity entity){
        TechnicianDTO dto=new TechnicianDTO();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public List<TechnicianDTO> getAll(){
        List<TechnicianDTO> dtos=new ArrayList<>();
        for(TechnicianDetailsEntity entity: technicianDetailsRepository.findAll()){
            dtos.add(EntityToDto(entity));
        }
        return dtos;
    }

    public TechnicianDTO getById(long id) throws UserNotFoundException {
        TechnicianDetailsEntity entity = technicianDetailsRepository.findById(id).orElse(null);
        if (entity != null) {
            return EntityToDto(entity);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }
    public TechnicianDTO saveDetails(TechnicianDTO dto){
        TechnicianDetailsEntity entity=DtoToEntity(dto);
        return EntityToDto(technicianDetailsRepository.save(entity));
    }

    public  void deleteById(long id) throws UserNotFoundException{
        TechnicianDetailsEntity entity= technicianDetailsRepository.findById(id).orElse(null);
        if(entity!=null){
            technicianDetailsRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }

}
