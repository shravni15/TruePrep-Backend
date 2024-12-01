package com.miniproject.TruePrep.Repository;

import com.miniproject.TruePrep.Model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeacherRepository extends MongoRepository<Teacher, String> {
    Optional<Teacher> findByEmail(String email);
    // Find a teacher by their assigned subject
    Optional<Teacher> findBySubject(String subject);
}
