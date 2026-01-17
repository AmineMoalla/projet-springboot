package com.iit.security;

import com.iit.entities.Etudiant;
import com.iit.entities.Formateur;

import jakarta.persistence.*;




@Entity
public class ApplicationUser {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Formateur formateur;

    @OneToOne(mappedBy = "user")
    private Etudiant etudiant;

    public enum Role {
        ADMIN, FORMATEUR, ETUDIANT
    }
    
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
}