package com.iit.services;

import com.iit.entities.Note;
import com.iit.repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    // Récupérer toutes les notes
    public List<Note> getAll() {
        return repository.findAll();
    }  

    // Récupérer une note par son id
    public Optional<Note> getById(Long id) {
        return repository.findById(id);
    }

    // Ajouter ou modifier une note
    /*public Note save(Note note) {
    	
        return repository.save(note);
    }*/
    
    public Note save(Note note) {
        if (note.getValeur() < 0 || note.getValeur() > 20) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 20");
        }
        return repository.save(note);
    }

    // Supprimer une note par id
    public void delete(Long id) {
    	
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
  
}
