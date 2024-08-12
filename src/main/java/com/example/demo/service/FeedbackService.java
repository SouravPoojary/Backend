package com.example.demo.service;


import com.example.demo.dto.FeedbackDTO;
import com.example.demo.entity.FeedbackEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.FeedbackRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FeedbackService {
        @Autowired
        private FeedbackRepository feedbackRepository;

        public FeedbackEntity DtoToEntity(FeedbackDTO dto){
            FeedbackEntity entity=new FeedbackEntity();
            BeanUtils.copyProperties(dto,entity);
            return entity;
        }

        public FeedbackDTO EntityToDto(FeedbackEntity entity){
            FeedbackDTO dto=new FeedbackDTO();
            BeanUtils.copyProperties(entity,dto);
            return dto;
        }

        public List<FeedbackDTO> getAll(){
            List<FeedbackDTO> dtos=new ArrayList<>();
            for(FeedbackEntity entity:feedbackRepository.findAll()){
                dtos.add(EntityToDto(entity));
            }
            return dtos;
        }

        public FeedbackDTO getById(long id) throws UserNotFoundException {
            FeedbackEntity entity = feedbackRepository.findById(id).orElse(null);
            if (entity != null) {
                return EntityToDto(entity);
            } else {
                throw new UserNotFoundException("Id not found");
            }
        }
        public FeedbackDTO saveDetails(FeedbackDTO dto){
            FeedbackEntity entity=DtoToEntity(dto);
            return EntityToDto(feedbackRepository.save(entity));
        }

        public  void deleteById(long id) throws UserNotFoundException{
            FeedbackEntity entity=feedbackRepository.findById(id).orElse(null);
            if(entity!=null){
                feedbackRepository.deleteById(id);
            } else {
                throw new UserNotFoundException("Id not found");
            }
        }


    }



