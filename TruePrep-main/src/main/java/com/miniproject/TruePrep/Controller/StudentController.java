package com.miniproject.TruePrep.Controller;

import com.miniproject.TruePrep.Model.Student;
import com.miniproject.TruePrep.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.miniproject.TruePrep.Model.StudentLoginRequest;

import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/students")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return ResponseEntity.ok("Student added successfully!");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody StudentLoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            throw new RuntimeException("Email and password are required");
        }
        // Validate email and password
        Student student = studentRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!student.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return ResponseEntity.ok("Login successful! Welcome " + student.getFirstName() + " " + student.getLastName());



    }

    // Approve student (specific to student self-approval, if needed)
    @PutMapping("/{id}/approve-self")
    public ResponseEntity<String> approveStudent(@PathVariable String id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student existingStudent = student.get();
            existingStudent.setStatus("Self-Approved");
            studentRepository.save(existingStudent);
            return ResponseEntity.ok("Student self-approved successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found!");
    }
}
