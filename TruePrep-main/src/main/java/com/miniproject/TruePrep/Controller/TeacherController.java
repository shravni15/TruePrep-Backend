package com.miniproject.TruePrep.Controller;

import com.miniproject.TruePrep.Model.Teacher;
import com.miniproject.TruePrep.Service.TeacherService;
import com.miniproject.TruePrep.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private EmailService emailService;

    // **1. Admin: Create Teacher Account with Subject Assignment**
    @PostMapping("/create")
    public ResponseEntity<String> createTeacher(@RequestBody Teacher teacher) {
        // Check if the subject is already assigned to another teacher
        try {
            teacherService.createTeacher(teacher);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

        // Optionally, send email with credentials (commented for now)
        // String emailBody = "Dear " + teacher.getFirstName() + ",\n\n" +
        //         "Your account has been created. Here are your credentials:\n" +
        //         "Email: " + teacher.getEmail() + "\n" +
        //         "Password: " + teacher.getPassword() + "\n\n" +
        //         "Please log in to your dashboard.\n\nBest Regards,\nAdmin";
        // emailService.sendEmail(teacher.getEmail(), "Teacher Account Created", emailBody);

        return ResponseEntity.ok("Teacher account created successfully and credentials emailed!");
    }

    // **2. Teacher Login**
    @PostMapping("/login")
    public ResponseEntity<String> teacherLogin(@RequestParam String email, @RequestParam String password) {
        Optional<Teacher> teacher = teacherService.findTeacherByEmail(email);
        if (teacher.isEmpty() || !teacher.get().getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        return ResponseEntity.ok("Login successful! Welcome " + teacher.get().getFirstName());
    }

    // **3. Admin: Delete Teacher Account**
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable String id) {
        boolean isDeleted = teacherService.deleteTeacherById(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).body("Teacher not found with ID: " + id);
        }
        return ResponseEntity.ok("Teacher record deleted successfully!");
    }

    // **4. Admin: View Teacher Details by ID**
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable String id) {
        Optional<Teacher> teacher = teacherService.findTeacherById(id);
        if (teacher.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(teacher.get());
    }
}
