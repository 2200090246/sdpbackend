package com.edu.erp.controller;

import com.edu.erp.model.User;
import com.edu.erp.repository.UserRepository;
import com.edu.erp.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    // Existing login functionality
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        String role = loginData.get("role");

        // Log the login attempt
        System.out.println("Login attempt: Username = " + username + ", Role = " + role);

        boolean isAuthenticated = authService.authenticate(username, password, role);

        if (isAuthenticated) {
            // Authentication successful
            return ResponseEntity.ok().body(Map.of("success", true, "role", role));
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Login failed. Check username, password, and role."));
        }
    }

    // Endpoint to Add User with any Role (Admin, Teacher, Student)
    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            // Log the input user data for debugging
            System.out.println("Received user data: " + user.toString());

            // Check if the user already exists with the same username and role
            Optional<User> existingUser = userRepository.findByUsernameAndRole(user.getUsername(), user.getRole());
            if (existingUser.isPresent()) {
                // Conflict: User already exists
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("success", false, "message", "User with the same username and role already exists."));
            }

            // Save the user with the provided role
            userRepository.save(user);
            // Return success response
            return ResponseEntity.ok(Map.of("success", true, "message", "User added successfully!"));

        } catch (Exception e) {
            // Log the error to understand what went wrong
            System.out.println("Error occurred while adding user: " + e.getMessage());
            // Return an internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "An error occurred while adding the user. Please try again."));
        }
    }

    // Endpoint to Update User Password
    @PutMapping("/users")
    public ResponseEntity<?> updateUserPassword(@RequestBody Map<String, String> requestData) {
        try {
            String username = requestData.get("username");
            String newPassword = requestData.get("password");

            // Log the update request for debugging
            System.out.println("Password update request: Username = " + username);

            // Check if the user exists
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                user.setPassword(newPassword); // Update the password
                userRepository.save(user); // Save the updated user

                // Log success and return response
                System.out.println("Password updated successfully for user: " + username);
                return ResponseEntity.ok(Map.of("success", true, "message", "Password updated successfully!"));
            } else {
                // User not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "User not found."));
            }

        } catch (Exception e) {
            // Log the error and return a failure response
            System.out.println("Error occurred while updating password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "An error occurred while updating the password. Please try again."));
        }
    }

    // Endpoint to Update User Details (Email, Profile Image, Attendance, Grade, Courses)
    @PutMapping("/users/details")
    public ResponseEntity<?> updateUserDetails(@RequestBody Map<String, String> userDetails) {
        try {
            String username = userDetails.get("username");
            String email = userDetails.get("email");
            String profileImage = userDetails.get("profileImage");
            Double attendance = Double.parseDouble(userDetails.get("attendance"));
            String grade = userDetails.get("grade");
            String courses = userDetails.get("courses");

            // Find the user by their username
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                user.setEmail(email);
                user.setProfileImage(profileImage);
                user.setAttendance(attendance);
                user.setGrade(grade);
                user.setCourses(courses);

                // Save the updated user details
                userRepository.save(user);

                return ResponseEntity.ok(Map.of("success", true, "message", "User details updated successfully!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "User not found."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error updating user details."));
        }
    }

    // Endpoint to get all students and their details
    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        try {
            // Fetch all users with the role of Student
            Optional<User> students = userRepository.findByUsername("Student");
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error fetching student details."));
        }
    }
    @DeleteMapping("/users/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        try {
            // Log the deletion request for debugging
            System.out.println("Delete request: Username = " + username);

            // Check if the user exists in the database
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent()) {
                // User found, proceed to delete
                userRepository.delete(existingUser.get());

                // Return success response
                return ResponseEntity.ok(Map.of("success", true, "message", "User deleted successfully!"));
            } else {
                // User not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "User not found."));
            }
        } catch (Exception e) {
            // Log the error and return failure response
            System.out.println("Error occurred while deleting user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "An error occurred while deleting the user. Please try again."));
        }
    }
 // Endpoint to get user details by username
    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUserDetails(@PathVariable String username) {
        try {
            // Attempt to find the user by username
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "User not found."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error fetching user details."));
        }
    }

 // Endpoint to search for a user by username
 // Endpoint to search for a user by username
    @GetMapping("/users/search")
    public ResponseEntity<?> searchUser(@RequestParam String username) {
        // Log the username being searched for (trimmed for spaces)
        System.out.println("Searching for user with username: " + username.trim()); 

        try {
            // Trim whitespace before searching
            username = username.trim();

            // Case-sensitive search for the exact username
            Optional<User> user = userRepository.findByUsername(username);

            if (user.isPresent()) {
                System.out.println("User found: " + user.get().getUsername());  // Log the found user
                return ResponseEntity.ok(user.get());
            } else {
                System.out.println("User not found for username: " + username);  // Log when no user is found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "User not found."));
            }
        } catch (Exception e) {
            // Log the error for debugging
            System.out.println("Error occurred while searching for user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error searching for user."));
        }
    }




}
