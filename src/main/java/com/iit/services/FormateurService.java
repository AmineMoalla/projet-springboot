package com.iit.services;

import com.iit.entities.Formateur;
import com.iit.repositories.FormateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormateurService {

    private final FormateurRepository repository;

    public FormateurService(FormateurRepository repository) {
        this.repository = repository;
    }

    public List<Formateur> getAll() {
        return repository.findAll();
    }

    public Formateur getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Formateur save(Formateur formateur) {
        return repository.save(formateur);
    }
   
    public void delete(Long id) {
        repository.deleteById(id);
    }
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
