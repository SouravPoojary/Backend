package com.example.demo.controller;

import com.example.demo.dto.TechnicianStatusDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.TechnicianStatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technician_status")
public class TechnicianStatusController {
    @Autowired
    private TechnicianStatusService technicianStatusService;

    @PostMapping("/create")
    public ResponseEntity<TechnicianStatusDTO> create(@RequestBody @Valid TechnicianStatusDTO technicianStatusDTO){
        TechnicianStatusDTO dto= technicianStatusService.saveDetails(technicianStatusDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TechnicianStatusDTO>> getAll(){
        List<TechnicianStatusDTO> list= technicianStatusService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<TechnicianStatusDTO> getById(@PathVariable long id) throws UserNotFoundException {
        TechnicianStatusDTO dto= technicianStatusService.getById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<TechnicianStatusDTO> update(@PathVariable long id,@RequestBody @Valid TechnicianStatusDTO technicianStatusDTO) throws UserNotFoundException{
        technicianStatusService.getById(id);
        technicianStatusDTO.setId(id);
        TechnicianStatusDTO dto= technicianStatusService.saveDetails(technicianStatusDTO);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TechnicianStatusDTO> delete(@PathVariable long id) throws UserNotFoundException{
        technicianStatusService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
