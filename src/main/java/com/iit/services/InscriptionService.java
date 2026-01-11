package com.iit.services;
import com.iit.entities.Groupe;
import com.iit.repositories.GroupeRepository;


import com.iit.entities.Inscription;
import com.iit.repositories.InscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService {
    private final GroupeRepository groupeRepository;
    public List<Inscription> getByGroupeId(Long groupeId) {
        return repository.findByGroupe_Id(groupeId);
    }

    public List<Inscription> getSansGroupe() {
        return repository.findByGroupeIsNull();
    }

    private final InscriptionRepository repository;

    public InscriptionService(InscriptionRepository repository, GroupeRepository groupeRepository) {
        this.repository = repository;
        this.groupeRepository = groupeRepository;
    }
    /**
     * Vérifie si on peut ajouter une inscription dans le groupe (capacité non atteinte)
     */
    public boolean canAddToGroupe(Long groupeId) {
        Groupe groupe = groupeRepository.findById(groupeId).orElse(null);
        if (groupe == null) return false;
        int capacite = groupe.getCapacite();
        int nbInscriptions = (groupe.getInscriptions() != null) ? groupe.getInscriptions().size() : 0;
        return nbInscriptions < capacite;
    }

    public List<Inscription> getAll() {
        return repository.findAll();
    }

    public Optional<Inscription> getById(Long id) {
        return repository.findById(id);
    }

    public Inscription save(Inscription inscription) {
        // Sécurité métier : empêcher toute inscription si la capacité est dépassée
        if (inscription.getGroupe() != null && inscription.getGroupe().getId() != null) {
            Long groupeId = inscription.getGroupe().getId();
            Groupe groupe = groupeRepository.findById(groupeId).orElse(null);
            if (groupe != null) {
                long nombreInscrits = repository.countByGroupeId(groupeId);
                System.out.println("[LOG] Groupe " + groupeId + " : inscrits=" + nombreInscrits + ", capacité=" + groupe.getCapacite());
                if (nombreInscrits >= groupe.getCapacite()) {
                    throw new RuntimeException("Capacité du groupe est atteinte");
                }
            }
        }
        System.out.println("[LOG] Ajout inscription pour groupe=" + (inscription.getGroupe() != null ? inscription.getGroupe().getId() : "null"));
        return repository.save(inscription);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

     public Inscription inscrireEtudiant(Inscription inscription) {

        if (inscription.getGroupe() == null || inscription.getGroupe().getId() == null) {
            throw new RuntimeException("Groupe non valide");
        }

        Long groupeId = inscription.getGroupe().getId();

        Groupe groupe = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new RuntimeException("Groupe introuvable"));

        long nombreInscrits = repository.countByGroupeId(groupeId);
        System.out.println("[LOG] (inscrireEtudiant) Groupe " + groupeId + " : inscrits=" + nombreInscrits + ", capacité=" + groupe.getCapacite());
        if (nombreInscrits >= groupe.getCapacite()) {
            throw new RuntimeException("Capacité du groupe atteinte");
        }

        System.out.println("[LOG] (inscrireEtudiant) Ajout inscription pour groupe=" + groupeId);

        return repository.save(inscription);
    }
}
