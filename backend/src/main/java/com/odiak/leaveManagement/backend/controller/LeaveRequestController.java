package com.odiak.leaveManagement.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.odiak.leaveManagement.backend.services.LeaveRequestService;
import com.odiak.leaveManagement.backend.models.LeaveRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
@CrossOrigin(origins = "http://localhost:4200")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/all")
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @GetMapping("/{id}")
    public LeaveRequest getLeaveRequestById(@PathVariable("id") Long id) {
        return leaveRequestService.getLeaveRequestById(id);
    }

    @PostMapping
    public LeaveRequest createLeaveRequest(@Valid @RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.createLeaveRequest(leaveRequest);
    }

    @PutMapping("/{id}")
    public LeaveRequest updateLeaveRequest(@PathVariable("id") Long id, @Valid @RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.updateLeaveRequest(id, leaveRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteLeaveRequest(@PathVariable("id") Long id) {
        leaveRequestService.deleteLeaveRequest(id);
    }
}
