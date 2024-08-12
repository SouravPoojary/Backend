package com.example.demo.controller;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/create")
    public ResponseEntity<FeedbackDTO> create(@RequestBody @Valid FeedbackDTO feedbackDTO){
        FeedbackDTO dto= feedbackService.saveDetails(feedbackDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FeedbackDTO>> getAll(){
        List<FeedbackDTO> list= feedbackService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<FeedbackDTO> getById(@PathVariable long id) throws UserNotFoundException {
        FeedbackDTO dto= feedbackService.getById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<FeedbackDTO> update(@PathVariable long id,@RequestBody @Valid FeedbackDTO feedbackDTO) throws UserNotFoundException{
        feedbackService.getById(id);
        feedbackDTO.setId(id);
        FeedbackDTO dto= feedbackService.saveDetails(feedbackDTO);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FeedbackDTO> delete(@PathVariable long id) throws UserNotFoundException{
        feedbackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
