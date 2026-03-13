package com.odiak.leaveManagement.backend.services;

import com.odiak.leaveManagement.backend.models.LeaveRequest;
import com.odiak.leaveManagement.backend.models.User;
import com.odiak.leaveManagement.backend.repositories.LeaveRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("LeaveRequestService Unit Tests")
class LeaveRequestServiceTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @InjectMocks
    private LeaveRequestService leaveRequestService;

    private LeaveRequest testLeaveRequest;
    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
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
    @DisplayName("Should retrieve all leave requests")
    void testGetAllLeaveRequests() {
        LeaveRequest leaveRequest2 = new LeaveRequest();
        leaveRequest2.setId(2L);
        leaveRequest2.setStartDate(LocalDate.of(2024, 4, 10));
        leaveRequest2.setEndDate(LocalDate.of(2024, 4, 12));
        leaveRequest2.setReason("Sick leave");
        leaveRequest2.setStatus(LeaveRequest.LeaveStatus.APPROVED);
        leaveRequest2.setUser(testUser);

        List<LeaveRequest> leaveRequests = Arrays.asList(testLeaveRequest, leaveRequest2);
        when(leaveRequestRepository.findAll()).thenReturn(leaveRequests);

        List<LeaveRequest> retrievedRequests = leaveRequestService.getAllLeaveRequests();

        assertEquals(2, retrievedRequests.size());
        assertEquals("Vacation", retrievedRequests.get(0).getReason());
        assertEquals("Sick leave", retrievedRequests.get(1).getReason());
        verify(leaveRequestRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve a leave request by ID")
    void testGetLeaveRequestById() {
        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(testLeaveRequest));

        LeaveRequest retrievedRequest = leaveRequestService.getLeaveRequestById(1L);

        assertNotNull(retrievedRequest);
        assertEquals(1L, retrievedRequest.getId());
        assertEquals("Vacation", retrievedRequest.getReason());
        verify(leaveRequestRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when leave request not found")
    void testGetLeaveRequestByIdNotFound() {
        when(leaveRequestRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> leaveRequestService.getLeaveRequestById(999L));
        verify(leaveRequestRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should create a leave request successfully")
    void testCreateLeaveRequest() {
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(testLeaveRequest);

        LeaveRequest createdRequest = leaveRequestService.createLeaveRequest(testLeaveRequest);

        assertNotNull(createdRequest);
        assertEquals("Vacation", createdRequest.getReason());
        assertEquals(LeaveRequest.LeaveStatus.PENDING, createdRequest.getStatus());
        verify(leaveRequestRepository, times(1)).save(testLeaveRequest);
    }

    @Test
    @DisplayName("Should update a leave request successfully")
    void testUpdateLeaveRequest() {
        LeaveRequest updatedDetails = new LeaveRequest();
        updatedDetails.setStartDate(LocalDate.of(2024, 3, 18));
        updatedDetails.setEndDate(LocalDate.of(2024, 3, 22));
        updatedDetails.setReason("Updated vacation");
        updatedDetails.setStatus(LeaveRequest.LeaveStatus.APPROVED);

        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(testLeaveRequest));
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(testLeaveRequest);

        LeaveRequest updatedRequest = leaveRequestService.updateLeaveRequest(1L, updatedDetails);

        assertEquals("Updated vacation", updatedRequest.getReason());
        assertEquals(LeaveRequest.LeaveStatus.APPROVED, updatedRequest.getStatus());
        verify(leaveRequestRepository, times(1)).findById(1L);
        verify(leaveRequestRepository, times(1)).save(testLeaveRequest);
    }

    @Test
    @DisplayName("Should delete a leave request successfully")
    void testDeleteLeaveRequest() {
        when(leaveRequestRepository.findById(1L)).thenReturn(Optional.of(testLeaveRequest));

        leaveRequestService.deleteLeaveRequest(1L);

        verify(leaveRequestRepository, times(1)).findById(1L);
        verify(leaveRequestRepository, times(1)).delete(testLeaveRequest);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent leave request")
    void testDeleteLeaveRequestNotFound() {
        when(leaveRequestRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> leaveRequestService.deleteLeaveRequest(999L));
        verify(leaveRequestRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should find leave requests by user")
    void testFindByUser() {
        List<LeaveRequest> userLeaveRequests = Arrays.asList(testLeaveRequest);
        when(leaveRequestRepository.findByUser(testUser)).thenReturn(userLeaveRequests);

        List<LeaveRequest> foundRequests = leaveRequestService.findByUser(testUser);

        assertEquals(1, foundRequests.size());
        assertEquals(testUser.getId(), foundRequests.get(0).getUser().getId());
        verify(leaveRequestRepository, times(1)).findByUser(testUser);
    }

    @Test
    @DisplayName("Should find leave requests by status")
    void testFindByStatus() {
        List<LeaveRequest> pendingRequests = Arrays.asList(testLeaveRequest);
        when(leaveRequestRepository.findByStatus(LeaveRequest.LeaveStatus.PENDING)).thenReturn(pendingRequests);

        List<LeaveRequest> foundRequests = leaveRequestService.findByStatus(LeaveRequest.LeaveStatus.PENDING);

        assertEquals(1, foundRequests.size());
        assertEquals(LeaveRequest.LeaveStatus.PENDING, foundRequests.get(0).getStatus());
        verify(leaveRequestRepository, times(1)).findByStatus(LeaveRequest.LeaveStatus.PENDING);
    }
}
