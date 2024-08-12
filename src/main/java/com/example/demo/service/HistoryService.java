package com.example.demo.service;

import com.example.demo.dto.ServiceHistoryDTO;
import com.example.demo.entity.HistoryEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ServiceHistoryRepository;
import com.example.demo.repository.ServiceHistoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class HistoryService {
    @Autowired
    private ServiceHistoryRepository serviceHistory;

    public HistoryEntity DtoToEntity(ServiceHistoryDTO dto){
        HistoryEntity entity=new HistoryEntity();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }

    public ServiceHistoryDTO EntityToDto(HistoryEntity entity){
        ServiceHistoryDTO dto=new ServiceHistoryDTO();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public List<ServiceHistoryDTO> getAll(){
        List<ServiceHistoryDTO> dtos=new ArrayList<>();
        for(HistoryEntity entity:serviceHistory.findAll()){
            dtos.add(EntityToDto(entity));
        }
        return dtos;
    }

    public ServiceHistoryDTO getById(long id) throws UserNotFoundException {
        HistoryEntity entity = serviceHistory.findById(id).orElse(null);
        if (entity != null) {
            return EntityToDto(entity);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }
    public ServiceHistoryDTO saveDetails(ServiceHistoryDTO dto){
        HistoryEntity entity=DtoToEntity(dto);
        return EntityToDto(serviceHistory.save(entity));
    }

    public  void deleteById(long id) throws UserNotFoundException{
        HistoryEntity entity= serviceHistory.findById(id).orElse(null);
        if(entity!=null){
            serviceHistory.deleteById(id);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }

}
