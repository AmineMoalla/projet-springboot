package com.iit.security;

import com.iit.entities.Etudiant;
import com.iit.entities.Formateur;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;




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
    @JsonIgnore
    private Formateur formateur;

    @OneToOne(mappedBy = "user")
    private Etudiant etudiant;
    private Long referenceId;

    public enum Role {
        ADMIN, FORMATEUR, ETUDIANT
    }
    public long getReferenceId() {
        return referenceId;
    }
    public void setReferenceId(long referenceId) {
        this.referenceId = referenceId;
    }
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public Formateur getFormateur() { return formateur; }
    public Etudiant getEtudiant() { return etudiant; }

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
    public void setFormateur(Formateur formateur) { this.formateur = formateur; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }

    // Génère un JWT avec l'id spécifique selon le rôle
    // Génère un JWT avec l'id spécifique selon le rôle
public String generateRoleBasedToken(long expiration, String secretKey) {
    Long realId = null; // <- ID réel du formateur ou étudiant
    if (getRole() == Role.FORMATEUR && getFormateur() != null) {
        realId = getFormateur().getId();
    } else if (getRole() == Role.ETUDIANT && getEtudiant() != null) {
        realId = getEtudiant().getId();
    }

    return io.jsonwebtoken.Jwts.builder()
        .setSubject(getEmail())
        .claim("role", getRole().name())
        .claim("realId", realId) // <-- ici
        .setIssuedAt(new java.util.Date())
        .setExpiration(new java.util.Date(System.currentTimeMillis() + expiration))
        .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secretKey)
        .compact();
}

}