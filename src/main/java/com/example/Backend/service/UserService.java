package com.example.Backend.service;

//import com.example.Backend.dto.UserDto;
import com.example.Backend.dto.SCenterDto;

import com.example.Backend.entity.User;
import com.example.Backend.exception.UserNotFoundException;
import com.example.Backend.repository.UserRepository;
import com.example.Backend.resource.Role;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//     private PasswordEncoder passwordEncoder; // Ensure you have a password encoder bean

//    public void createDefaultAdmin() {
//        User adminUser = userRepository.findByEmail("admin@gmail.com");
//        if (adminUser==null) {
//            User admin = new User();
//            admin.setEmail("admin@gmail.com");
//            admin.setPassword(passwordEncoder.encode("admin123"));
////                    passwordEncoder.encode("admin123")); // Store hashed password
//            admin.setRole(Role.ADMIN);
//
//            userRepository.save(admin);
//        }
//    }
//
//
//    public List<User> getAll() {
//        List<User> user = new ArrayList<>();
//        for (User entity : userRepository.findAll()) {
//            user.add(entity);
//        }
//        return user;
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//        Customer customer=customerRepository.findByEmail(email);
//        if(customer!=null){
//            return buildUser(customer.getEmail(), customer.getPassword(), customer.getRole());
//        }

        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

//    SCenter sCenter=sCenterRepository.findByEmail(email);
//        if(sCenter!=null){
//            return buildUser(sCenter.getEmail(),sCenter.getPassword(),sCenter.getRole());
//        }
//
//
//        User admin=userRepository.findByEmail(email);
//        if(admin!=null){
//            return  buildUser(admin.getEmail(),admin.getPassword(),admin.getRole());
//        }
//        throw new UsernameNotFoundException("Email not registered: " + email);
//    }
//private  UserDetails buildUser(String email,String password,Role role){
//        return org.springframework.security.core.userdetails.User
//                .withUsername(email)
//                .password(password)
//                .authorities(role.name())
//                .build();
//}
//}

//