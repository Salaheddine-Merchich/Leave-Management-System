package com.odiak.leaveManagement.backend.integration;

import com.odiak.leaveManagement.backend.BaseIntegrationTest;
import com.odiak.leaveManagement.backend.models.LeaveRequest;
import com.odiak.leaveManagement.backend.models.User;
import com.odiak.leaveManagement.backend.repositories.LeaveRequestRepository;
import com.odiak.leaveManagement.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("LeaveRequest Controller Integration Tests")
public class LeaveRequestControllerIntegrationTest extends BaseIntegrationTest {

        @Autowired
        private LeaveRequestRepository leaveRequestRepository;

        @Autowired
        private UserRepository userRepository;

        private User testUser;

        @BeforeEach
        void setUp() {
                leaveRequestRepository.deleteAll();
                userRepository.deleteAll();

                testUser = new User();
                testUser.setName("Requester");
                testUser.setEmail("requester@example.com");
                testUser.setPassword("pass");
                testUser.setRole(User.Role.EMPLOYEE);
                testUser = userRepository.save(testUser);
        }

        @Test
        @DisplayName("Should create and retrieve a leave request")
        void shouldCreateAndRetrieveLeaveRequest() throws Exception {
                LeaveRequest request = new LeaveRequest();
                request.setStartDate(LocalDate.now().plusDays(1));
                request.setEndDate(LocalDate.now().plusDays(5));
                request.setReason("Vacation");
                request.setStatus(LeaveRequest.LeaveStatus.PENDING);
                request.setUser(testUser);

                // Create request
                mockMvc.perform(post("/api/leave-requests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.reason").value("Vacation"))
                                .andExpect(jsonPath("$.user.id").value(testUser.getId()));

                // Retrieve all
                mockMvc.perform(get("/api/leave-requests/all"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)))
                                .andExpect(jsonPath("$[0].reason").value("Vacation"));
        }

        @Test
        @DisplayName("Should update leave request status")
        void shouldUpdateLeaveRequestStatus() throws Exception {
                LeaveRequest request = new LeaveRequest();
                request.setStartDate(LocalDate.now().plusDays(1));
                request.setEndDate(LocalDate.now().plusDays(2));
                request.setReason("Sick Leave");
                request.setStatus(LeaveRequest.LeaveStatus.PENDING);
                request.setUser(testUser);
                LeaveRequest savedRequest = leaveRequestRepository.save(request);

                LeaveRequest updatedRequest = new LeaveRequest();
                updatedRequest.setStartDate(savedRequest.getStartDate());
                updatedRequest.setEndDate(savedRequest.getEndDate());
                updatedRequest.setReason(savedRequest.getReason());
                updatedRequest.setUser(testUser);
                updatedRequest.setStatus(LeaveRequest.LeaveStatus.APPROVED);

                mockMvc.perform(put("/api/leave-requests/" + savedRequest.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedRequest)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value("APPROVED"));
        }

        @Test
        @DisplayName("Should delete an existing leave request")
        void shouldDeleteLeaveRequest() throws Exception {
                LeaveRequest request = new LeaveRequest(LocalDate.now(), LocalDate.now().plusDays(1), "Del",
                                LeaveRequest.LeaveStatus.PENDING, testUser);
                LeaveRequest saved = leaveRequestRepository.save(request);

                mockMvc.perform(delete("/api/leave-requests/" + saved.getId()))
                                .andExpect(status().isOk());

                mockMvc.perform(get("/api/leave-requests/" + saved.getId()))
                                .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("Should return 500 when getting non-existent leave request")
        void shouldReturn500WhenLeaveRequestNotFound() throws Exception {
                mockMvc.perform(get("/api/leave-requests/999"))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath("$.message", containsString("Leave request not found")));
        }

        @Test
        @DisplayName("Should handle empty list of leave requests")
        void shouldHandleEmptyList() throws Exception {
                leaveRequestRepository.deleteAll(); // Already done in setUp, but being explicit
                mockMvc.perform(get("/api/leave-requests/all"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        @DisplayName("Should return 400 when leave request has blank reason")
        void shouldReturn400WhenBlankReason() throws Exception {
                LeaveRequest invalidRequest = new LeaveRequest(LocalDate.now(), LocalDate.now().plusDays(1), "",
                                LeaveRequest.LeaveStatus.PENDING, testUser);

                mockMvc.perform(post("/api/leave-requests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidRequest)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.errors.reason").value("Reason is required"));
        }

        @Test
        @DisplayName("Should return 500 when end date is before start date")
        void shouldReturn500WhenInvalidDateRange() throws Exception {
                LeaveRequest invalidRequest = new LeaveRequest(LocalDate.now().plusDays(5), LocalDate.now().plusDays(1),
                                "Vacation",
                                LeaveRequest.LeaveStatus.PENDING, testUser);

                mockMvc.perform(post("/api/leave-requests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidRequest)))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath("$.message").value("End date cannot be before start date"));
        }
}
