

package com.iit.services; 

import com.iit.entities.Groupe;
import com.iit.repositories.GroupeRepository;



import com.iit.entities.AffectationCours;
import com.iit.repositories.AffectationRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AffectationCoursService {

    private final AffectationRepository repository;
    private final GroupeRepository groupeRepository;

    public AffectationCoursService(AffectationRepository repository, GroupeRepository groupeRepository) {
        this.repository = repository;
        this.groupeRepository = groupeRepository;
    }
   
    public boolean canAffectToGroupe(Long groupeId) {
        Groupe groupe = groupeRepository.findById(groupeId).orElse(null);
        if (groupe == null) return false;
        int capacite = groupe.getCapacite();
        int nbAffectations = (groupe.getAffectationsCours() != null) ? groupe.getAffectationsCours().size() : 0;
        return nbAffectations < capacite;
    }

    public List<AffectationCours> getAll() {
        return repository.findAll();
    }
 
    public Optional<AffectationCours> getById(Long id) {
        return repository.findById(id);
    }

    public AffectationCours save(AffectationCours affectation) {
        return repository.save(affectation);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }


        public AffectationCours saveWithCapacityCheck(AffectationCours affectation) {
        if (affectation.getGroupe() == null || affectation.getGroupe().getId() == null) {
            return null;
        }
        Long groupeId = affectation.getGroupe().getId();
        if (!canAffectToGroupe(groupeId)) {
            return null;
        }
        return repository.save(affectation);
    }
}
