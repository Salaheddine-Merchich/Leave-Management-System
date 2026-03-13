package com.odiak.leaveManagement.backend.services;

import com.odiak.leaveManagement.backend.models.User;
import com.odiak.leaveManagement.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("UserService Unit Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John Doe");
        testUser.setEmail("john@example.com");
        testUser.setPassword("password123");
        testUser.setRole(User.Role.EMPLOYEE);
    }

    @Test
    @DisplayName("Should create a new user successfully")
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User createdUser = userService.createUser(testUser);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        assertEquals("john@example.com", createdUser.getEmail());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    @DisplayName("Should retrieve all users")
    void testGetAllUsers() {
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Smith");
        user2.setEmail("jane@example.com");
        user2.setPassword("password456");
        user2.setRole(User.Role.ADMIN);

        List<User> users = Arrays.asList(testUser, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> retrievedUsers = userService.getAllUsers();

        assertEquals(2, retrievedUsers.size());
        assertEquals("John Doe", retrievedUsers.get(0).getName());
        assertEquals("Jane Smith", retrievedUsers.get(1).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve a user by ID")
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        User retrievedUser = userService.getUserById(1L);

        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getId());
        assertEquals("John Doe", retrievedUser.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void testGetUserByIdNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(999L));
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should update a user successfully")
    void testUpdateUser() {
        User updatedUserDetails = new User();
        updatedUserDetails.setName("John Updated");
        updatedUserDetails.setEmail("john.updated@example.com");
        updatedUserDetails.setPassword("newpassword123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User updatedUser = userService.updateUser(1L, updatedUserDetails);

        assertEquals("John Updated", updatedUser.getName());
        assertEquals("john.updated@example.com", updatedUser.getEmail());
        assertEquals("newpassword123", updatedUser.getPassword());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    @DisplayName("Should delete a user successfully")
    void testDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent user")
    void testDeleteUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.deleteUser(999L));
        verify(userRepository, times(1)).findById(999L);
    }
}
