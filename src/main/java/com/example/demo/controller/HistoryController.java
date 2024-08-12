package com.example.demo.controller;

import com.example.demo.dto.ServiceHistoryDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.HistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @PostMapping("/create")
    public ResponseEntity<ServiceHistoryDTO> create(@RequestBody @Valid ServiceHistoryDTO serviceHistoryDTO){
        ServiceHistoryDTO dto= historyService.saveDetails(serviceHistoryDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ServiceHistoryDTO>> getAll(){
        List<ServiceHistoryDTO> list= historyService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<ServiceHistoryDTO> getById(@PathVariable long id) throws UserNotFoundException {
        ServiceHistoryDTO dto= historyService.getById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<ServiceHistoryDTO> update(@PathVariable long id,@RequestBody @Valid ServiceHistoryDTO serviceHistoryDTO) throws UserNotFoundException{
        historyService.getById(id);
        serviceHistoryDTO.setId(id);
        ServiceHistoryDTO dto= historyService.saveDetails(serviceHistoryDTO);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ServiceHistoryDTO> delete(@PathVariable long id) throws UserNotFoundException{
        historyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
