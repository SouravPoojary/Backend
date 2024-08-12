package com.example.demo.service;

import com.example.demo.entity.UserDetailsEntity;
import com.example.demo.repository.TechnicianDetailsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VehicleOwnerRepository;
import com.example.demo.resource.Role;
import com.example.demo.response.AuthenticationResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;
    @Autowired
    private TechnicianDetailsRepository technicianDetailsRepository;


    public AuthenticationResponse  register(UserDetailsEntity request) {
//        if (userRepository.existsById(request.getId())) {
//            return new AuthenticationResponse("User Id is already having an account!");
//        }
        if (request.getRole() == Role.TECHNICIAN) {
            if (!technicianDetailsRepository.existsById((long) request.getId()))
                return new AuthenticationResponse("there is no such technician");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthenticationResponse("Username is already taken");
        } else if (userRepository.existsByPassword(request.getPassword())) {
            return new AuthenticationResponse("Password is already taken");
        } else {
            UserDetailsEntity user = new UserDetailsEntity();
            BeanUtils.copyProperties(request, user);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user = userRepository.save(user);

            String token = jwtService.generatetoken(user);
            return new AuthenticationResponse(token);
        }
    }

    public AuthenticationResponse login(UserDetails request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        UserDetailsEntity user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.generatetoken(user);

        return new AuthenticationResponse(token);
    }
}
