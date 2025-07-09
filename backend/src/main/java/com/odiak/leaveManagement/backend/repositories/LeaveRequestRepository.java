package com.odiak.leaveManagement.backend.repositories;

import org.springframework.stereotype.Repository;
import com.odiak.leaveManagement.backend.models.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.odiak.leaveManagement.backend.models.User;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    // Additional query methods can be defined here if needed

    List<LeaveRequest> findByUser(User user);
    List<LeaveRequest> findByStatus(LeaveRequest.LeaveStatus status);
    
}
