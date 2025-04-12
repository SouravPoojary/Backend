package com.example.Backend.controller;

import com.example.Backend.entity.User;
import com.example.Backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import java.awt.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/customers")
    public ResponseEntity<List<User>> getAllCustomers() {
        return ResponseEntity.ok(authenticationService.getAllCustomers());
    }

    @GetMapping("/service-centers")
    public ResponseEntity<List<User>> getAllServiceCenters() {
        return ResponseEntity.ok(authenticationService.getAllServiceCenters());
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        authenticationService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @DeleteMapping("/service-centers/{id}")
    public ResponseEntity<String> deleteServiceCenter(@PathVariable Long id) {
        authenticationService.deleteServiceCenter(id);
        return ResponseEntity.ok("Service Center deleted successfully");
    }
}
