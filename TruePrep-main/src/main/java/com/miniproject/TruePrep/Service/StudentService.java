package com.miniproject.TruePrep.Service;

import com.miniproject.TruePrep.Model.Student;
import com.miniproject.TruePrep.Model.StudentAlreadyExistsException;
import com.miniproject.TruePrep.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void registerStudent(Student student) {
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new StudentAlreadyExistsException("A student with this email already exists!");
        }
        student.setStatus("Pending"); // Default status
        studentRepository.save(student);
    }
}

