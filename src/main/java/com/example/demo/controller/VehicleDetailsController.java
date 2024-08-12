package com.example.demo.controller;

import com.example.demo.dto.VehicleDetailsDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.VehicleDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehicle_details")
public class VehicleDetailsController{
    @Autowired
    private VehicleDetailsService vehicleDetailsService;

    @PostMapping("/create")
    public ResponseEntity<VehicleDetailsDTO> create(@RequestBody @Valid VehicleDetailsDTO vehicleDetailsDTO){
        VehicleDetailsDTO dto= vehicleDetailsService.saveDetails(vehicleDetailsDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<VehicleDetailsDTO>> getAll(){
        List<VehicleDetailsDTO> list= vehicleDetailsService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<VehicleDetailsDTO> getById(@PathVariable long id) throws UserNotFoundException {
        VehicleDetailsDTO dto= vehicleDetailsService.getById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<VehicleDetailsDTO> update(@PathVariable long id,@RequestBody @Valid VehicleDetailsDTO vehicleDetailsDTO) throws UserNotFoundException{
        vehicleDetailsService.getById(id);
        vehicleDetailsDTO.setId(id);
        VehicleDetailsDTO dto= vehicleDetailsService.saveDetails(vehicleDetailsDTO);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<VehicleDetailsDTO> delete(@PathVariable long id) throws UserNotFoundException{
        vehicleDetailsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
