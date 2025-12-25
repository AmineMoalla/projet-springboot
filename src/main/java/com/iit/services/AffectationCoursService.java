package com.iit.services;


import com.iit.entities.AffectationCours;
import com.iit.repositories.AffectationRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AffectationCoursService {

    private final AffectationRepository repository;

    public AffectationCoursService(AffectationRepository repository) {
        this.repository = repository;
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
}
