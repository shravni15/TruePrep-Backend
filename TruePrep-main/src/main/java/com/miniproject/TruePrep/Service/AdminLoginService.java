package com.miniproject.TruePrep.Service;

import com.miniproject.TruePrep.Repository.AdminRepository;
import com.miniproject.TruePrep.Model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AdminLoginService {
    private static final Logger logger = LoggerFactory.getLogger(AdminLoginService.class);

    private final AdminRepository adminRepository;

    @Autowired
    public AdminLoginService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Validates the admin login credentials.
     *
     * @param username the admin's username
     * @param password the admin's password
     * @return boolean indicating if the admin is authenticated
     */
    public boolean validateAdmin(String username, String password) {
        // Log the attempt to validate the admin login
        logger.debug("Attempting to validate admin with username: {}", username);

        // Find the admin by username and validate the password
        return adminRepository.findByUsername(username)
                .map(admin -> {
                    boolean isPasswordValid = admin.getPassword().equals(password);

                    // Log password validation result
                    logger.debug("Password validation result: {}", isPasswordValid);

                    return isPasswordValid;
                })
                .orElseGet(() -> {
                    // Log warning if admin not found
                    logger.warn("Admin with username '{}' not found", username);

                    return false;
                });
    }
}
