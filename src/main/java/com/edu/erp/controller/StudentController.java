package com.edu.erp.controller;

import com.edu.erp.model.StudentDetails;
import com.edu.erp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentDetails> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public StudentDetails createStudent(@RequestBody StudentDetails studentDetails) {
        return studentService.saveStudent(studentDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
