package com.edu.erp.service;

import com.edu.erp.model.StudentDetails;
import com.edu.erp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Get all students
    public List<StudentDetails> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    public StudentDetails getStudentById(Long id) {
        Optional<StudentDetails> student = studentRepository.findById(id);
        return student.orElse(null); // Return null if not found
    }

    // Save student details
    public StudentDetails saveStudent(StudentDetails studentDetails) {
        return studentRepository.save(studentDetails);
    }

    // Update student details
    public StudentDetails updateStudent(Long id, StudentDetails studentDetails) {
        if (studentRepository.existsById(id)) {
            studentDetails.setId(id);
            return studentRepository.save(studentDetails);
        } else {
            return null; // Return null if student not found
        }
    }

    // Delete student by ID
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
