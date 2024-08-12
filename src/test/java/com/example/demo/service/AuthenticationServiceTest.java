package com.example.demo.service;


import com.example.demo.entity.UserDetailsEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private UserDetailsEntity user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserDetailsEntity();
        user.setId(101);
        user.setUsername("sharat");
        user.setPassword("password");
    }

    @Test
    public void testRegisterUsernameTaken() {
        when(userRepository.existsByUsername("sharat")).thenReturn(true);

        AuthenticationResponse response = authenticationService.register(user);

        assertEquals("Username is already taken", response.getToken());
        verify(userRepository, never()).existsByPassword(anyString());
        verify(userRepository, never()).save(any(UserDetailsEntity.class));
    }

    @Test
    public void testRegisterPasswordTaken() {
        when(userRepository.existsByUsername("sharat")).thenReturn(false);
        when(userRepository.existsByPassword("password")).thenReturn(true);

        AuthenticationResponse response = authenticationService.register(user);

        assertEquals("Password is already taken", response.getToken());
        verify(userRepository, never()).save(any(UserDetailsEntity.class));
    }

    @Test
    public void testRegisterSuccess() {
        when(userRepository.existsByUsername("sharat")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(UserDetailsEntity.class))).thenAnswer(invocation -> {
            UserDetailsEntity savedUser = invocation.getArgument(0);
            savedUser.setId(1);
            return savedUser;
        });
        when(jwtService.generatetoken(any(UserDetailsEntity.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(user);

        assertNotNull(response.getToken());
        verify(userRepository, times(1)).save(any(UserDetailsEntity.class));
        verify(jwtService, times(1)).generatetoken(any(UserDetailsEntity.class));
    }

    @Test
    public void testLoginSuccess() {
        UserDetails userDetails = User.withUsername("sharat").password("password").roles("USER").build();
        when(userRepository.findByUsername("sharat")).thenReturn(Optional.of(user));
        when(jwtService.generatetoken(any(UserDetailsEntity.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.login(userDetails);

        assertNotNull(response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generatetoken(any(UserDetailsEntity.class));
    }

    @Test
    public void testLoginFailure() {
        UserDetails userDetails = User.withUsername("sharat").password("wrongPassword").roles("USER").build();
        when(userRepository.findByUsername("sharat")).thenReturn(Optional.of(user));
        doThrow(new RuntimeException("Bad credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authenticationService.login(userDetails);
        });

        assertEquals("Bad credentials", exception.getMessage());
        verify(jwtService, never()).generatetoken(any(UserDetailsEntity.class));
    }
}
