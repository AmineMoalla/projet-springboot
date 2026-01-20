package com.iit.services;

import com.iit.entities.Note;
import com.iit.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import com.iit.dtos.*;

import java.util.List;
import java.util.Optional;
import com.iit.repositories.*;
import com.iit.entities.*;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;


    public NoteService(NoteRepository noteRepository, EtudiantRepository etudiantRepository, CoursRepository coursRepository) {
        this.noteRepository = noteRepository;
        this.etudiantRepository = etudiantRepository;
        this.coursRepository = coursRepository;
    }

    // Récupérer toutes les notes
    public List<Note> getAll() {
        return noteRepository.findAll();
    }  

    // Récupérer une note par son id
    public Optional<Note> getById(Long id) {
        return noteRepository.findById(id);
    }

    // Ajouter ou modifier une note
    /*public Note save(Note note) {
    	
        return repository.save(note);
    }*/
    
    public Note save(Note note) {
        if (note.getValeur() < 0 || note.getValeur() > 20) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 20");
        }
        return noteRepository.save(note);
    }

    // Supprimer une note par id
    public void delete(Long id) {
    	
        noteRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return noteRepository.existsById(id);
    }

    public NoteDTO saveOrUpdateNote(NoteDTO noteDTO) {
    System.out.println("Requête pour créer/modifier note : " + noteDTO);

    // Vérifier l'étudiant
    Etudiant etudiant = etudiantRepository.findById(noteDTO.getEtudiantId())
            .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

    // Vérifier le cours
    Cours cours = coursRepository.findById(noteDTO.getCoursId())
            .orElseThrow(() -> new RuntimeException("Cours introuvable"));

    Note note;
    if(noteDTO.getId() != null) {
        note = noteRepository.findById(noteDTO.getId()).orElse(new Note());
    } else {
        // Vérifier si l'étudiant a déjà une note pour ce cours
        note = noteRepository.findByEtudiantAndCours(etudiant, cours).orElse(new Note());
    }

    note.setEtudiant(etudiant);
    note.setCours(cours);
    note.setValeur(noteDTO.getValeur());

    noteRepository.save(note);

    // Mettre à jour l'ID dans le DTO pour renvoyer la note créée/modifiée
    noteDTO.setId(note.getId());
    return noteDTO;
}


public List<NoteAffichageDTO> getNotesEtudiant(Long etudiantId) {
    return noteRepository.findNotesByEtudiant(etudiantId);
}
  
}
