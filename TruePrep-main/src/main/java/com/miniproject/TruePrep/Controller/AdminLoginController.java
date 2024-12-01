package com.miniproject.TruePrep.Controller;

import com.miniproject.TruePrep.Model.AdminLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.miniproject.TruePrep.Model.StudentNotFoundException;
import com.miniproject.TruePrep.Model.Student;
import com.miniproject.TruePrep.Model.Admin;
import com.miniproject.TruePrep.Repository.StudentRepository;
import com.miniproject.TruePrep.Repository.AdminRepository;
import com.miniproject.TruePrep.Service.AdminLoginService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Constructor for dependency injection
    public AdminLoginController(AdminLoginService adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

    // Admin Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest loginRequest) {
        boolean isValid = adminLoginService.validateAdmin(loginRequest.getUsername(), loginRequest.getPassword());
        if (isValid) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Admin Registration (Optional)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Admin admin) {
        adminRepository.save(admin);
        return ResponseEntity.ok("Admin registered successfully!");
    }

    // View all students
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
            // Check if a student with the given email already exists
            if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("A student with this email already exists!");
            }

            // Set default status if not provided
            if (student.getStatus() == null || student.getStatus().isEmpty()) {
                student.setStatus("Pending");
            }

            studentRepository.save(student);
            return ResponseEntity.ok("Student added successfully!");
    }


    // Approve a student
    @PutMapping("/students/{id}/approve")
    public ResponseEntity<String> approveStudent(@PathVariable String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        student.setStatus("Approved");
        studentRepository.save(student);
        return ResponseEntity.ok("Student approved successfully!");
    }

    // Reject a student
    @PutMapping("/students/{id}/reject")
    public ResponseEntity<String> rejectStudent(@PathVariable String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        student.setStatus("Rejected");
        studentRepository.save(student);
        return ResponseEntity.ok("Student rejected successfully!");
    }

    // Delete a student
    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        studentRepository.deleteById(id);
        return ResponseEntity.ok("Student deleted successfully!");
    }
}
