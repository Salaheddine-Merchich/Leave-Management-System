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
import com.odiak.leaveManagement.backend.models.User;

import java.util.List;
@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/all")
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }
    @GetMapping("/{id}")
    public LeaveRequest getLeaveRequestById(@PathVariable Long id) {
        return leaveRequestService.getLeaveRequestById(id);
    }
    @GetMapping("/user/{userId}")
    public List<LeaveRequest> getLeaveRequestsByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        return leaveRequestService.findByUser(user);
    }



}
