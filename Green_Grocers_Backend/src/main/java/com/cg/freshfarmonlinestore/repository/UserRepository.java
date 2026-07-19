package com.cg.freshfarmonlinestore.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cg.freshfarmonlinestore.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

    // Find a user by username
    Optional<User> findByUserName(String userName);

    // Check if a user exists by email
    Boolean existsByEmail(String email);
    
    // Check if a user exists by username
    Boolean existsByUserName(String userName);
    
    // Find a user by username or email
    Optional<User> findByUserNameOrEmail(String userName, String email);
}
