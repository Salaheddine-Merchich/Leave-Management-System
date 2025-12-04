package com.odiak.leaveManagement.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.odiak.leaveManagement.backend.repositories.LeaveRequestRepository;
import com.odiak.leaveManagement.backend.models.LeaveRequest;
import java.util.List;
import com.odiak.leaveManagement.backend.models.User;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }
    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));
    }
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }
    public LeaveRequest updateLeaveRequest(Long id, LeaveRequest leaveRequestDetails) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        leaveRequest.setStartDate(leaveRequestDetails.getStartDate());
        leaveRequest.setEndDate(leaveRequestDetails.getEndDate());
        leaveRequest.setReason(leaveRequestDetails.getReason());
        leaveRequest.setStatus(leaveRequestDetails.getStatus());
        return leaveRequestRepository.save(leaveRequest);
    }
    public void deleteLeaveRequest(Long id) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        leaveRequestRepository.delete(leaveRequest);
    }
    public List<LeaveRequest> findByUser(User user) {
        return leaveRequestRepository.findByUser(user);
    }
    public List<LeaveRequest> findByStatus(LeaveRequest.LeaveStatus status){
        return leaveRequestRepository.findByStatus(status);
    }
}
