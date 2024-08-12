package com.example.demo.controller;

import com.example.demo.dto.VehicleOwnerDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.VehicleOwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehicle_owner")
public class VehicleOwnerController {
    @Autowired
    private VehicleOwnerService vehicleOwnerService;

    @PostMapping("/create")
    public ResponseEntity<VehicleOwnerDTO> create(@RequestBody @Valid VehicleOwnerDTO vehicleOwnerDTO){
        VehicleOwnerDTO dto= vehicleOwnerService.saveDetails(vehicleOwnerDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<VehicleOwnerDTO>> getAll(){
        List<VehicleOwnerDTO> list= vehicleOwnerService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<VehicleOwnerDTO> getById(@PathVariable long id) throws UserNotFoundException {
        VehicleOwnerDTO dto= vehicleOwnerService.getById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<VehicleOwnerDTO> update(@PathVariable long id,@RequestBody @Valid VehicleOwnerDTO vehicleOwnerDTO) throws UserNotFoundException{
        vehicleOwnerService.getById(id);
        vehicleOwnerDTO.setId(id);
        VehicleOwnerDTO dto= vehicleOwnerService.saveDetails(vehicleOwnerDTO);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) throws UserNotFoundException{
        vehicleOwnerService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
