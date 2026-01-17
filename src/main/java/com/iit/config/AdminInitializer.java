package com.iit.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.iit.repositories.ApplicationUserRepository;
import com.iit.security.ApplicationUser;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final ApplicationUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByEmail("admin@iit.com").isEmpty()){
            ApplicationUser admin = new ApplicationUser();
            admin.setEmail("admin@iit.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(ApplicationUser.Role.ADMIN);
            userRepository.save(admin);
        }
    }
}