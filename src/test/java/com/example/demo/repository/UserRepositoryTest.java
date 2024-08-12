package com.example.demo.repository;

import com.example.demo.entity.UserDetailsEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private UserDetailsEntity user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserDetailsEntity();
        user.setName("John Doe");
        user.setUsername("johndoe");
        user.setPassword("password123");
    }

    @Test
    public void testFindByUsernameSuccess() {
        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));
        Optional<UserDetailsEntity> foundUser = userRepository.findByUsername("johndoe");
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    @Test
    public void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());
        Optional<UserDetailsEntity> foundUser = userRepository.findByUsername("nonexistentuser");
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testSaveUser() {
        UserDetailsEntity newUser = new UserDetailsEntity();
        newUser.setName("Jane Doe");
        newUser.setUsername("janedoe");
        newUser.setPassword("password456");
        when(userRepository.save(any(UserDetailsEntity.class))).thenReturn(newUser);

        UserDetailsEntity savedUser = userRepository.save(newUser);
        assertNotNull(savedUser);
        assertEquals("Jane Doe", savedUser.getName());
        assertEquals("janedoe", savedUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).delete(user);
        userRepository.delete(user);
        verify(userRepository, times(1)).delete(user);
    }
}
