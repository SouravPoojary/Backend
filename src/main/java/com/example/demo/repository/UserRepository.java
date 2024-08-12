package com.example.demo.repository;

import com.example.demo.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserDetailsEntity,Integer> {
Optional<UserDetailsEntity> findByUsername(String username);

    Boolean existsByPassword(String s);

    Boolean existsByUsername(String sharat);
}
