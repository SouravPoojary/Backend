package com.example.demo.controller;

import com.example.demo.dto.ServiceAppointmentDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class ServiceAppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<ServiceAppointmentDTO> create(@RequestBody @Valid ServiceAppointmentDTO serviceAppointmentDTO){
        ServiceAppointmentDTO dto=appointmentService.saveDetails(serviceAppointmentDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ServiceAppointmentDTO>> getAll(){
        List<ServiceAppointmentDTO> list=appointmentService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<ServiceAppointmentDTO> getById(@PathVariable long id) throws UserNotFoundException{
        ServiceAppointmentDTO dto=appointmentService.getById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<ServiceAppointmentDTO> update(@PathVariable long id,@RequestBody @Valid ServiceAppointmentDTO serviceAppointmentDTO) throws UserNotFoundException{
       appointmentService.getById(id);
       serviceAppointmentDTO.setId(id);
       ServiceAppointmentDTO dto=appointmentService.saveDetails(serviceAppointmentDTO);
       return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ServiceAppointmentDTO> delete(@PathVariable long id) throws UserNotFoundException{
       appointmentService.deleteById(id);
       return ResponseEntity.noContent().build();
    }


}
