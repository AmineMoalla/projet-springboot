package com.iit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.security.ApplicationUser;

import java.util.Optional;
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByEmail(String email);
     
}
