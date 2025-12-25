package com.iit.services;

import com.iit.entities.Etudiant;
import com.iit.repositories.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

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

    public Etudiant save(Etudiant etudiant) {
        return repository.save(etudiant);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
