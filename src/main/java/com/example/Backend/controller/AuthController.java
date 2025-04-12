package com.example.Backend.controller;

import com.example.Backend.dto.AuthRequestDto;
import com.example.Backend.dto.CustomerDto;
import com.example.Backend.dto.SCenterDto;
import com.example.Backend.response.AuthenticationResponse;
import com.example.Backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register/customer")
    public ResponseEntity<AuthenticationResponse> registerCustomer(
            @RequestBody CustomerDto request
    ) {
        return ResponseEntity.ok(authenticationService.registerCustomer(request));
    }

    @PostMapping("/register/service-center")
    public ResponseEntity<AuthenticationResponse> registerSCenter(
            @RequestBody SCenterDto request
    ) {
        return ResponseEntity.ok(authenticationService.registerSCenter(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthRequestDto request) {

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
