package com.odiak.leaveManagement.backend.controller;

import com.odiak.leaveManagement.backend.models.LeaveRequest;
import com.odiak.leaveManagement.backend.models.User;
import com.odiak.leaveManagement.backend.services.LeaveRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("LeaveRequestController Unit Tests")
class LeaveRequestControllerTest {

    @Mock
    private LeaveRequestService leaveRequestService;

    @InjectMocks
    private LeaveRequestController leaveRequestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private LeaveRequest testLeaveRequest;
    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(leaveRequestController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John Doe");
        testUser.setEmail("john@example.com");
        testUser.setRole(User.Role.EMPLOYEE);

        testLeaveRequest = new LeaveRequest();
        testLeaveRequest.setId(1L);
        testLeaveRequest.setStartDate(LocalDate.of(2024, 3, 15));
        testLeaveRequest.setEndDate(LocalDate.of(2024, 3, 20));
        testLeaveRequest.setReason("Vacation");
        testLeaveRequest.setStatus(LeaveRequest.LeaveStatus.PENDING);
        testLeaveRequest.setUser(testUser);
    }

    @Test
    @DisplayName("Should get all leave requests with status 200")
    void testGetAllLeaveRequests() throws Exception {
        LeaveRequest leaveRequest2 = new LeaveRequest();
        leaveRequest2.setId(2L);
        leaveRequest2.setStartDate(LocalDate.of(2024, 4, 10));
        leaveRequest2.setEndDate(LocalDate.of(2024, 4, 12));
        leaveRequest2.setReason("Sick leave");
        leaveRequest2.setStatus(LeaveRequest.LeaveStatus.APPROVED);
        leaveRequest2.setUser(testUser);

        List<LeaveRequest> leaveRequests = Arrays.asList(testLeaveRequest, leaveRequest2);
        when(leaveRequestService.getAllLeaveRequests()).thenReturn(leaveRequests);

        mockMvc.perform(get("/api/leave-requests/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reason").value("Vacation"))
                .andExpect(jsonPath("$[1].reason").value("Sick leave"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(leaveRequestService, times(1)).getAllLeaveRequests();
    }

    @Test
    @DisplayName("Should get leave request by ID with status 200")
    void testGetLeaveRequestById() throws Exception {
        when(leaveRequestService.getLeaveRequestById(1L)).thenReturn(testLeaveRequest);

        mockMvc.perform(get("/api/leave-requests/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reason").value("Vacation"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(leaveRequestService, times(1)).getLeaveRequestById(1L);
    }

    @Test
    @DisplayName("Should create leave request with status 200")
    void testCreateLeaveRequest() throws Exception {
        when(leaveRequestService.createLeaveRequest(any(LeaveRequest.class))).thenReturn(testLeaveRequest);

        mockMvc.perform(post("/api/leave-requests")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testLeaveRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reason").value("Vacation"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(leaveRequestService, times(1)).createLeaveRequest(any(LeaveRequest.class));
    }

    @Test
    @DisplayName("Should update leave request with status 200")
    void testUpdateLeaveRequest() throws Exception {
        LeaveRequest updatedRequest = new LeaveRequest();
        updatedRequest.setId(1L);
        updatedRequest.setStartDate(LocalDate.of(2024, 3, 18));
        updatedRequest.setEndDate(LocalDate.of(2024, 3, 22));
        updatedRequest.setReason("Updated vacation");
        updatedRequest.setStatus(LeaveRequest.LeaveStatus.APPROVED);
        updatedRequest.setUser(testUser);

        when(leaveRequestService.updateLeaveRequest(anyLong(), any(LeaveRequest.class))).thenReturn(updatedRequest);

        mockMvc.perform(put("/api/leave-requests/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("Updated vacation"))
                .andExpect(jsonPath("$.status").value("APPROVED"));

        verify(leaveRequestService, times(1)).updateLeaveRequest(anyLong(), any(LeaveRequest.class));
    }

    @Test
    @DisplayName("Should delete leave request with status 200")
    void testDeleteLeaveRequest() throws Exception {
        doNothing().when(leaveRequestService).deleteLeaveRequest(1L);

        mockMvc.perform(delete("/api/leave-requests/1"))
                .andExpect(status().isOk());

        verify(leaveRequestService, times(1)).deleteLeaveRequest(1L);
    }
}
