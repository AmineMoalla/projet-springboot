package com.iit.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iit.dtos.LoginRequest;
import com.iit.dtos.RegisterRequest;
import com.iit.entities.Etudiant;
import com.iit.entities.Formateur;
import com.iit.repositories.ApplicationUserRepository;
import com.iit.repositories.EtudiantRepository;
import com.iit.repositories.FormateurRepository;
import com.iit.security.ApplicationUser;

@Service
public class AuthenticationService {

    private final ApplicationUserRepository userRepository;
    private final EtudiantRepository etudiantRepository;
    private final FormateurRepository formateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(
            ApplicationUserRepository userRepository,
            EtudiantRepository etudiantRepository,
            FormateurRepository formateurRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.etudiantRepository = etudiantRepository;
        this.formateurRepository = formateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // ---------------- LOGIN ----------------
    // public String login(LoginRequest request) {

    //     ApplicationUser user = userRepository.findByEmail(request.getEmail())
    //             .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    //     if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    //         throw new RuntimeException("Mot de passe incorrect");
    //     }

    //     return jwtService.generateToken(user.getEmail(), user.getId(), user.getRole());
    // }
public String login(LoginRequest request) {
    ApplicationUser user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Mot de passe incorrect");
    }

    Long realId = null;

    switch (user.getRole()) {
        case ETUDIANT:
            Etudiant etudiant = etudiantRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
            realId = etudiant.getId();
            break;
        case FORMATEUR:
            Formateur formateur = formateurRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Formateur non trouvé"));
            realId = formateur.getId();
            break;
        case ADMIN:
            realId = user.getId(); // pour admin on garde l'id user
            break;
    }
return jwtService.generateToken(user);

    // return jwtService.generateToken(user.getEmail(), user.getId(), user.getRole(), realId);
}

    // ---------------- REGISTER ----------------
    public void register(RegisterRequest request) {

    	 // 1️⃣ créer ApplicationUser
        ApplicationUser user = new ApplicationUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(ApplicationUser.Role.valueOf(request.getRole()));
        user = userRepository.save(user);

        // 2️⃣ créer profil métier
        if (user.getRole() == ApplicationUser.Role.ETUDIANT) {
            Etudiant e = new Etudiant();
            e.setUser(user);
            e.setNom(request.getNom());
            e.setPrenom(request.getPrenom());
            e.setMatricule(request.getMatricule());
            e.setDateInscription(java.time.LocalDate.now());
            etudiantRepository.save(e);

        } else if (user.getRole() == ApplicationUser.Role.FORMATEUR) {
            Formateur f = new Formateur();
            f.setUser(user);
            f.setNom(request.getNom());
            f.setSpecialite(request.getSpecialite());
            formateurRepository.save(f);
        }
        // ADMIN → rien à créer
}
    
}