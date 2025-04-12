package com.example.Backend.controller;

import com.example.Backend.dto.AppointmentDto;
import com.example.Backend.entity.Appointment;
import com.example.Backend.exception.UserNotFoundException;

import com.example.Backend.resource.Role;
import com.example.Backend.resource.Status;
import com.example.Backend.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<AppointmentDto> create(@RequestBody @Valid AppointmentDto dto) {
        return new ResponseEntity<>(appointmentService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AppointmentDto>> getAll() {
        return new ResponseEntity<>(appointmentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AppointmentDto> getById(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity<>(appointmentService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestHeader(value = "userId" ) Long userId, @RequestHeader(value = "role") Role role) throws UserNotFoundException {
        appointmentService.deleteById(id,userId,role);
        return ResponseEntity.ok("Deleted the record with id = " + id);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
//        return appointmentService.updateStatus(id, status);
        String statusValue = requestBody.get("status");
        Status newStatus = Status.valueOf(statusValue);
        Appointment updatedAppointment = appointmentService.updateStatus(id, newStatus);
        return ResponseEntity.ok(updatedAppointment);
    }

    @PutMapping("addJob/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment updatedAppointment
    ) {

        Appointment appointment = appointmentService.updateAppointment(id, updatedAppointment);
        return ResponseEntity.ok(appointment);
    }
}
