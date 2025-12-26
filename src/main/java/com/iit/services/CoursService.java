package com.iit.services;

import com.iit.entities.Cours;
import com.iit.repositories.CoursRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoursService {

    private final CoursRepository repository;

    public CoursService(CoursRepository repository) {
        this.repository = repository;
    }

    public List<Cours> getAll() {
        return repository.findAll();
    }

    public Optional<Cours> getById(Long id) {
        return repository.findById(id);
    }

    public Cours save(Cours cours) {
        return repository.save(cours);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
