package com.miniproject.TruePrep.Repository;

import com.miniproject.TruePrep.Model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByUsername(String username);  // Ensure this returns Optional<Admin>
}
