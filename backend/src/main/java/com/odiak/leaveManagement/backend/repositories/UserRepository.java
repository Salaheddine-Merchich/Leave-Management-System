package com.odiak.leaveManagement.backend.repositories;

import org.springframework.stereotype.Repository;
import com.odiak.leaveManagement.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.odiak.leaveManagement.backend.models.LeaveRequest;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Additional query methods can be defined here if needed
}
