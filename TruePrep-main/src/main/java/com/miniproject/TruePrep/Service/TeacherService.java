package com.miniproject.TruePrep.Service;

import com.miniproject.TruePrep.Model.Teacher;
import com.miniproject.TruePrep.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    // Method to create a new teacher and assign a subject
    public void createTeacher(Teacher teacher) {
        // Check if the subject is already assigned to another teacher
        Optional<Teacher> existingTeacher = teacherRepository.findBySubject(teacher.getSubject());
        if (existingTeacher.isPresent()) {
            throw new RuntimeException("The subject " + teacher.getSubject() + " is already assigned to another teacher.");
        }

        // Save the new teacher if no conflict
        teacherRepository.save(teacher);
    }

    // Method to find a teacher by email (existing method)
    public Optional<Teacher> findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    // Method to find teacher by ID (existing method)
    public Optional<Teacher> findTeacherById(String id) {
        return teacherRepository.findById(id);
    }

    // Method to delete teacher by ID (existing method)
    public boolean deleteTeacherById(String id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            teacherRepository.delete(teacher.get());
            return true;
        }
        return false;
    }
}
