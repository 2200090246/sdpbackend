package com.edu.erp.controller;

import com.edu.erp.dto.StudentRequest;
import com.edu.erp.model.StudentDetails;
import com.edu.erp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS for React frontend
public class AdminController {

    @Autowired
    private StudentService studentService;

    // Get all students
    @GetMapping("/students")
    public ResponseEntity<List<StudentDetails>> getAllStudents() {
        List<StudentDetails> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Get a single student by ID
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDetails> getStudentById(@PathVariable Long id) {
        StudentDetails student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }

    // Add a new student
    @PostMapping("/students")
    public ResponseEntity<StudentDetails> addStudent(@RequestBody StudentRequest studentRequest) {
        StudentDetails studentDetails = new StudentDetails();
        studentDetails.setFirstName(studentRequest.getFirstName());
        studentDetails.setLastName(studentRequest.getLastName());
        studentDetails.setEmail(studentRequest.getEmail());
        studentDetails.setPhone(studentRequest.getPhone());
        studentDetails.setAddress(studentRequest.getAddress());
        studentDetails.setEnrollmentNumber(studentRequest.getEnrollmentNumber());

        StudentDetails createdStudent = studentService.saveStudent(studentDetails);
        return ResponseEntity.status(201).body(createdStudent); // Return 201 (Created) status
    }

    // Update an existing student
    @PutMapping("/students/{id}")
    public ResponseEntity<StudentDetails> updateStudent(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        StudentDetails studentDetails = new StudentDetails();
        studentDetails.setFirstName(studentRequest.getFirstName());
        studentDetails.setLastName(studentRequest.getLastName());
        studentDetails.setEmail(studentRequest.getEmail());
        studentDetails.setPhone(studentRequest.getPhone());
        studentDetails.setAddress(studentRequest.getAddress());
        studentDetails.setEnrollmentNumber(studentRequest.getEnrollmentNumber());

        StudentDetails updatedStudent = studentService.updateStudent(id, studentDetails);
        if (updatedStudent != null) {
            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if student not found
        }
    }

    // Delete a student by ID
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build(); // Return 204 (No Content) status
    }
}
