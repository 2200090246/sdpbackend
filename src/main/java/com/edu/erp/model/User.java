package com.edu.erp.model;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    private String username;
    private String password;   // Add password field
    private String role;       // Add role field
    private String email;
    private String profileImage;
    private Double attendance;
    private String grade;
    private String courses;

    // Constructor
    public User() {
    }

    // Getters and Setters for all fields

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {  // Setter for password
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {  // Setter for role
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Double getAttendance() {
        return attendance;
    }

    public void setAttendance(Double attendance) {
        this.attendance = attendance;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", attendance=" + attendance +
                ", grade='" + grade + '\'' +
                ", courses='" + courses + '\'' +
                '}';
    }
}
