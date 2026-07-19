package com.cg.freshfarmonlinestore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.freshfarmonlinestore.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Find an Admin by username or email
    Optional<Admin> findByUserNameOrEmail(String userName, String email);
    
    // Check if an Admin exists by email
    Boolean existsByEmail(String email);
    
    // Check if an Admin exists by username
    Boolean existsByUserName(String userName);
    
    // Find the maximum Admin ID in the Admin table
    @Query("SELECT COALESCE(MAX(c.adminId), 0) FROM Admin c")
    long findMaxCustomerId();
}
