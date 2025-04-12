package com.example.Backend.controller;

import com.example.Backend.dto.ServicesDto;
import com.example.Backend.exception.UserNotFoundException;
import com.example.Backend.resource.Role;
import com.example.Backend.service.ServicesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins = "http://localhost:3000")
public class ServicesController {
    @Autowired
    private ServicesService servicesService;

    @PostMapping("/create")
//    @ResponseBody
//@PostMapping(value = "/create", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ServicesDto> create(@RequestBody @Valid ServicesDto dto){
        return new ResponseEntity<>(servicesService.create(dto), HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ServicesDto>> getAll(){
        return new ResponseEntity<>(servicesService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<ServicesDto> getById(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity<>(servicesService.getById(id),HttpStatus.OK);
    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<ServicesDto> update(@RequestBody @Valid ServicesDto dto,@PathVariable Long id) throws UserNotFoundException {
//        servicesService.getById(id);
//        dto.setId(id);
//        return new ResponseEntity<>(servicesService.create(dto),HttpStatus.ACCEPTED);
//    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,@RequestHeader(value = "userId") Long userId,@RequestHeader Role role) throws UserNotFoundException {
        if (userId == null || role == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing userId or role in headers");
        }
        servicesService.deleteById(id,userId,role);
        return ResponseEntity.ok("deleted the record with id ="+id);
    }
}
