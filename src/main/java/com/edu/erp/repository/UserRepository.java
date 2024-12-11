package com.edu.erp.repository;

import com.edu.erp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Add this method to fetch a user by their usernamek
    Optional<User> findByUsername(String username);

    // Existing method
    Optional<User> findByUsernameAndRole(String username, String role);

	
}
