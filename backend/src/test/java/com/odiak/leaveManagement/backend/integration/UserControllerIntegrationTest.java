package com.odiak.leaveManagement.backend.integration;

import com.odiak.leaveManagement.backend.BaseIntegrationTest;
import com.odiak.leaveManagement.backend.models.User;
import com.odiak.leaveManagement.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("User Controller Integration Tests")
public class UserControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create and then retrieve a user")
    void shouldCreateAndRetrieveUser() throws Exception {
        User user = new User();
        user.setName("John Integration");
        user.setEmail("integration@example.com");
        user.setPassword("password123");
        user.setRole(User.Role.EMPLOYEE);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Integration"))
                .andExpect(jsonPath("$.email").value("integration@example.com"));

        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("John Integration"));
    }

    @Test
    @DisplayName("Should update an existing user")
    void shouldUpdateUser() throws Exception {
        User user = new User();
        user.setName("John Original");
        user.setEmail("original@example.com");
        user.setPassword("password123");
        user.setRole(User.Role.EMPLOYEE);
        User savedUser = userRepository.save(user);

        User updatedUser = new User();
        updatedUser.setName("John Updated");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setPassword("newpassword");
        updatedUser.setRole(User.Role.ADMIN);

        mockMvc.perform(put("/api/users/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    @DisplayName("Should delete an existing user")
    void shouldDeleteUser() throws Exception {
        User user = new User();
        user.setName("Delete Me");
        user.setEmail("delete@example.com");
        user.setPassword("password123");
        user.setRole(User.Role.EMPLOYEE);
        User savedUser = userRepository.save(user);

        mockMvc.perform(delete("/api/users/" + savedUser.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Should return 500 when getting non-existent user")
    void shouldReturn500WhenUserNotFound() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containsString("User not found")));
    }

    @Test
    @DisplayName("Should return 500 when creating user with duplicate email")
    void shouldReturn500WhenDuplicateEmail() throws Exception {
        User user1 = new User("User 1", "same@example.com", "pass", User.Role.EMPLOYEE);
        userRepository.save(user1);

        User user2 = new User("User 2", "same@example.com", "pass", User.Role.EMPLOYEE);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should return 500 when updating non-existent user")
    void shouldReturn500WhenUpdateNonExistent() throws Exception {
        User updatedUser = new User("Updated", "upd@example.com", "pass", User.Role.ADMIN);

        mockMvc.perform(put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should return 400 when user has invalid email")
    void shouldReturn400WhenInvalidEmail() throws Exception {
        User invalidUser = new User("Test", "not-an-email", "pass", User.Role.EMPLOYEE);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email").value("Invalid email format"));
    }

    @Test
    @DisplayName("Should return 400 when user has blank fields")
    void shouldReturn400WhenBlankFields() throws Exception {
        User invalidUser = new User("", "", "", User.Role.EMPLOYEE);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").value("Name is required"))
                .andExpect(jsonPath("$.errors.email").value("Email is required"))
                .andExpect(jsonPath("$.errors.password").value("Password is required"));
    }
}
