package com.iit.services;

import com.iit.entities.Inscription;
import com.iit.repositories.InscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService {

    private final InscriptionRepository repository;

    public InscriptionService(InscriptionRepository repository) {
        this.repository = repository;
    }

    public List<Inscription> getAll() {
        return repository.findAll();
    }

    public Optional<Inscription> getById(Long id) {
        return repository.findById(id);
    }

    public Inscription save(Inscription inscription) {
        return repository.save(inscription);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
