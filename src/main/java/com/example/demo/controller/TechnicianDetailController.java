package com.example.demo.controller;

import com.example.demo.dto.TechnicianDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.TechnicianDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("technician_details")
public class TechnicianDetailController {
    @Autowired
    private TechnicianDetailsService technicianDetailsService;

    @PostMapping("/create")
    public ResponseEntity<TechnicianDTO> create(@RequestBody @Valid TechnicianDTO technicianDTO){
        TechnicianDTO dto= technicianDetailsService.saveDetails(technicianDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TechnicianDTO>> getAll(){
        List<TechnicianDTO> list= technicianDetailsService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<TechnicianDTO> getById(@PathVariable long id) throws UserNotFoundException {
        TechnicianDTO dto= technicianDetailsService.getById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<TechnicianDTO> update(@PathVariable long id,@RequestBody @Valid TechnicianDTO technicianDTO) throws UserNotFoundException{
        technicianDetailsService.getById(id);
        technicianDTO.setId(id);
        TechnicianDTO dto= technicianDetailsService.saveDetails(technicianDTO);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TechnicianDTO> delete(@PathVariable long id) throws UserNotFoundException{
        technicianDetailsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
