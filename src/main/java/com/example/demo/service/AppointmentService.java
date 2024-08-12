package com.example.demo.service;

import com.example.demo.dto.ServiceAppointmentDTO;
import com.example.demo.entity.ServiceAppointmentEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ServiceAppointmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private ServiceAppointmentRepository serviceAppointmentRepository;

    public ServiceAppointmentEntity DtoToEntity(ServiceAppointmentDTO dto){
        ServiceAppointmentEntity entity=new ServiceAppointmentEntity();
        BeanUtils.copyProperties(dto,entity);
//        entity.setTechnicianDetailsEntity(dto.getTechnicianDetailsEntity());
        return entity;
    }

    public ServiceAppointmentDTO EntityToDto(ServiceAppointmentEntity entity){
        ServiceAppointmentDTO dto=new ServiceAppointmentDTO();
        BeanUtils.copyProperties(entity,dto);
//        dto.setTechnicianDetailsEntity(entity.getTechnicianDetailsEntity());
        return dto;
    }

    public List<ServiceAppointmentDTO> getAll(){
        List<ServiceAppointmentDTO> dtos=new ArrayList<>();
        for(ServiceAppointmentEntity entity:serviceAppointmentRepository.findAll()){
            dtos.add(EntityToDto(entity));
        }
        return dtos;
    }

    public ServiceAppointmentDTO getById(long id) throws UserNotFoundException {
        ServiceAppointmentEntity entity = serviceAppointmentRepository.findById(id).orElse(null);
        if (entity != null) {
            return EntityToDto(entity);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }
    public ServiceAppointmentDTO saveDetails(ServiceAppointmentDTO dto){
        ServiceAppointmentEntity entity=DtoToEntity(dto);
        return EntityToDto(serviceAppointmentRepository.save(entity));
    }

    public  void deleteById(long id) throws UserNotFoundException{
        ServiceAppointmentEntity entity=serviceAppointmentRepository.findById(id).orElse(null);
        if(entity!=null){
            serviceAppointmentRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Id not found");
        }
    }
//    public ServiceAppointmentDTO getById(long id) throws UserNotFoundException {
//        ServiceAppointmentEntity entity = serviceAppointmentRepository.findByServiceTypeEntity(id).orElse(null);
//        if (entity != null) {
//            return EntityToDto(entity);
//        } else {
//            throw new UserNotFoundException("Id not found");
//        }
//    }


}
