package com.odiak.leaveManagement.backend.repositories;

import org.springframework.stereotype.Repository;
import com.odiak.leaveManagement.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByOrderByIdAsc();
}
