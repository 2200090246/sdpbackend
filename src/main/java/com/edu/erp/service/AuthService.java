package com.edu.erp.service;

import com.edu.erp.repository.UserRepository;
import com.edu.erp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String password, String role) {
        Optional<User> user = userRepository.findByUsernameAndRole(username, role);

        if (user.isEmpty()) {
            System.out.println("Authentication failed: User with username " + username + " and role " + role + " not found.");
            return false;
        }

        if (!user.get().getPassword().equals(password)) {
            System.out.println("Authentication failed: Incorrect password for username " + username + ".");
            return false;
        }

        // Authentication successful
        System.out.println("Authentication successful for username: " + username + ", Role: " + role);
        return true;
    }

    // New Method to Add Student
    public boolean addStudent(String username, String password) {
        Optional<User> existingStudent = userRepository.findByUsernameAndRole(username, "STUDENT");

        if (existingStudent.isPresent()) {
            System.out.println("Failed to add student: Username " + username + " already exists.");
            return false;
        }

        User newStudent = new User();
        newStudent.setUsername(username);
        newStudent.setPassword(password);
        newStudent.setRole("STUDENT");

        userRepository.save(newStudent);
        System.out.println("Student added successfully: Username = " + username);
        return true;
    }
}
