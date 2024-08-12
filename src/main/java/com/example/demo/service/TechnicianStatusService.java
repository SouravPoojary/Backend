package com.example.demo.service;

import com.example.demo.dto.TechnicianStatusDTO;
import com.example.demo.entity.TechnicianStatusEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.TechnicianStatusRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TechnicianStatusService {
    @Autowired
    private TechnicianStatusRepository technicianStatusRepository;

    public TechnicianStatusEntity DtoToEntity(TechnicianStatusDTO dto){
        TechnicianStatusEntity entity=new TechnicianStatusEntity();
//        entity.setStatus(dto.getStatus());
//        entity.setTechnicianDetailsEntity(dto.getTechnicianEntity());
//        entity.setId(dto.getId());
//        entity.setUpdateDate(dto.getUpdateDate());
         BeanUtils.copyProperties(dto,entity);
        return entity;
    }


    public TechnicianStatusDTO EntityToDto(TechnicianStatusEntity entity){
        TechnicianStatusDTO dto=new TechnicianStatusDTO();
//        dto.setStatus(entity.getStatus());
//        dto.setTechnicianEntity(entity.getTechnicianDetailsEntity());
//        dto.setId(entity.getId());
//        dto.setUpdateDate(entity.getUpdateDate());
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public List<TechnicianStatusDTO> getAll(){
        List<TechnicianStatusDTO> dtos=new ArrayList<>();
        for(TechnicianStatusEntity entity: technicianStatusRepository.findAll()){
            dtos.add(EntityToDto(entity));
        }
        return dtos;
    }

    public TechnicianStatusDTO getById(long id) throws UserNotFoundException {
        TechnicianStatusEntity entity = technicianStatusRepository.findById(id).orElse(null);
        if (entity != null) {
            return EntityToDto(entity);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }
    public TechnicianStatusDTO saveDetails(TechnicianStatusDTO dto){
        TechnicianStatusEntity entity=DtoToEntity(dto);
        return EntityToDto(technicianStatusRepository.save(entity));
    }

    public  void deleteById(long id) throws UserNotFoundException{
        TechnicianStatusEntity entity= technicianStatusRepository.findById(id).orElse(null);
        if(entity!=null){
            technicianStatusRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }
}
