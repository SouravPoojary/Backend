package com.example.demo.controller;


import com.example.demo.entity.UserDetailsEntity;
import com.example.demo.response.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterSuccess() {
        UserDetailsEntity request = new UserDetailsEntity();
        AuthenticationResponse response = new AuthenticationResponse("xyz");

        when(authenticationService.register(any(UserDetailsEntity.class))).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authenticationController.register(request);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testLoginSuccess() {
        UserDetailsEntity request = new UserDetailsEntity();
        AuthenticationResponse response = new AuthenticationResponse("xyz");

        when(authenticationService.login(any(UserDetailsEntity.class))).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = authenticationController.login(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        UserDetailsEntity request = new UserDetailsEntity();
        when(authenticationService.register(any(UserDetailsEntity.class))).thenThrow(new RuntimeException("User already exists"));

        try {
            authenticationController.register(request);
        } catch (RuntimeException e) {
            assertEquals("User already exists", e.getMessage());
        }
    }

    @Test
    public void testLoginUserNotFound() {
        UserDetailsEntity request = new UserDetailsEntity();
        when(authenticationService.login(any(UserDetailsEntity.class))).thenThrow(new RuntimeException("User not found"));

        try {
            authenticationController.login(request);
        } catch (RuntimeException e) {
            assertEquals("User not found", e.getMessage());
        }
    }
}
