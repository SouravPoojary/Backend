package com.example.demo.controller;

import com.example.demo.dto.ServiceTypeOptionDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.ServiceTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("service_type")
public class ServiceTypeController {
    @Autowired
    private ServiceTypeService typeService;

    @PostMapping("/create")
    public ResponseEntity<ServiceTypeOptionDTO> create(@RequestBody @Valid ServiceTypeOptionDTO serviceTypeOptionDTO){
        ServiceTypeOptionDTO dto= typeService.saveDetails(serviceTypeOptionDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ServiceTypeOptionDTO>> getAll(){
        List<ServiceTypeOptionDTO> list= typeService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<ServiceTypeOptionDTO> getById(@PathVariable long id) throws UserNotFoundException {
        ServiceTypeOptionDTO dto= typeService.getById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<ServiceTypeOptionDTO> update(@PathVariable long id,@RequestBody @Valid ServiceTypeOptionDTO serviceTypeOptionDTO) throws UserNotFoundException{
        typeService.getById(id);
        serviceTypeOptionDTO.setId(id);
        ServiceTypeOptionDTO dto= typeService.saveDetails(serviceTypeOptionDTO);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ServiceTypeOptionDTO> delete(@PathVariable long id) throws UserNotFoundException{
        typeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
