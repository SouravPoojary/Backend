package com.example.Backend.repository;

import com.example.Backend.entity.User;
import com.example.Backend.resource.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);

    Optional<User> existsByIdAndRole(Long id, Role role);
    void deleteByIdAndRole(Long id, Role role);

//    Boolean existsByUsername(String email);
//    Boolean existsByPassword(String password);
}
