package com.example.Backend.service;


import com.example.Backend.dto.AuthRequestDto;
import com.example.Backend.dto.CustomerDto;
import com.example.Backend.dto.SCenterDto;
import com.example.Backend.entity.User;
import com.example.Backend.repository.UserRepository;
import com.example.Backend.resource.Role;
import com.example.Backend.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UserRepository  userRepository ;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
     private JwtService jwtService;
//    @Autowired
//    private AuthenticationManager authenticationManager;
public AuthenticationResponse registerCustomer(CustomerDto request) {
    var user = User.builder()
            .fullname(request.getFullname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .contact(request.getContact())
            .address(request.getAddress())
            .role(Role.CUSTOMER)
            .build();

    userRepository.save(user);

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .role(user.getRole().name())
            .build();
}

    public AuthenticationResponse registerSCenter(SCenterDto request) {
        var user = User.builder()
                .fullname(request.getFullname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .contact(request.getContact())
                .shopname(request.getShopname())
                .address(request.getAddress())
                .role(Role.SERVICE_CENTER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .build();
    }

    public AuthenticationResponse authenticate(AuthRequestDto request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .build();
    }

    public List<User> getAllCustomers() {
        return userRepository.findByRole(Role.CUSTOMER);
    }

    public List<User> getAllServiceCenters() {
        return userRepository.findByRole(Role.SERVICE_CENTER);
    }

    public void deleteCustomer(Long id) {
        if (userRepository.existsByIdAndRole(id, Role.CUSTOMER).isEmpty()) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public void deleteServiceCenter(Long id) {
        if (userRepository.existsByIdAndRole(id, Role.SERVICE_CENTER).isEmpty()) {
            throw new RuntimeException("Service Center not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}

//