package com.iit.services;

import com.iit.entities.*;
import com.iit.repositories.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
public class EtudiantService {
    @org.springframework.beans.factory.annotation.Autowired
    private com.iit.repositories.ApplicationUserRepository userRepository;

    private final EtudiantRepository repository;

    public EtudiantService(EtudiantRepository repository) {
        this.repository = repository;
    }

    public List<Etudiant> getAll() {
        return repository.findAll();
    }

    public Optional<Etudiant> getById(Long id) {
        return repository.findById(id);
    }

    // Génère un matricule à 6 chiffres
    private String genererMatricule() {
        int min = 100000;
        int max = 999999;
        int value = min + (int)(Math.random() * ((max - min) + 1));
        return String.valueOf(value);
    }

    public Etudiant save(Etudiant etudiant) {
        // Initialisation des champs à la création
        if (etudiant.getId() == null) {
            etudiant.setDateInscription(java.time.LocalDate.now());
            etudiant.setMatricule(genererMatricule());
            // Si l'utilisateur est fourni, on l'enregistre (email doit être présent)
            if (etudiant.getUser() != null) {
                etudiant.getUser().setRole(com.iit.security.ApplicationUser.Role.ETUDIANT);
                if (etudiant.getUser().getPassword() == null) {
                    etudiant.getUser().setPassword("");
                }
                userRepository.save(etudiant.getUser());
            } else {
                throw new IllegalArgumentException("L'utilisateur (email) doit être fourni pour l'étudiant.");
            }
        }
        return repository.save(etudiant);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<Etudiant> getEtudiantsByFormateur(Long formateurId) {
    return repository.findEtudiantsByFormateur(formateurId);
}

   public List<Cours> getCoursByEtudiant(Long etudiantId) {
        return repository.findById(etudiantId)
                .map(Etudiant::getCoursList)
                .orElse(Collections.emptyList());
    }

}
