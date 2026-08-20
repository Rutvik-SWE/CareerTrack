package com.careertrack.service;

import com.careertrack.entity.User;
import com.careertrack.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        // Test 2: User registration
        // This test ensures that when a user registers, their password is encrypted
        // and the userRepository.save() method is called correctly.
        
        User user = new User("John Doe", "john@example.com", "password123");
        
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword123");
        
        userService.registerUser(user);
        
        assertEquals("hashedPassword123", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }
    
    @Test
    void testFindByEmail() {
        User user = new User("John Doe", "john@example.com", "password123");
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        
        Optional<User> found = userService.findByEmail("john@example.com");
        
        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getName());
    }
}
