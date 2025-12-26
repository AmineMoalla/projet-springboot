package com.iit.services;

import com.iit.entities.Groupe;
import com.iit.repositories.GroupeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupeService {

    private final GroupeRepository repository;

    public GroupeService(GroupeRepository repository) {
        this.repository = repository;
    }

    public List<Groupe> getAll() {
        return repository.findAll();
    }

    public Optional<Groupe> getById(Long id) {
        return repository.findById(id);
    }
 
    public Groupe save(Groupe groupe) {
        return repository.save(groupe);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
