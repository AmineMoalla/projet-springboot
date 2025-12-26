package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Note;
import com.iit.repositories.NoteRepository;
import com.iit.services.NoteService;

@RestController
@RequestMapping("/api/notes")
public class NoteRestController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/")
    public List<Note> getAll() {
        return noteService.getAll();
    }

    @GetMapping("/{id}")
    public Note getById(@PathVariable Long id) {
        return noteService.getById(id).orElse(null);
    }

    @PostMapping("/")
    public Note save(@RequestBody Note n) {
        return noteService.save(n);
    }

    /*PutMapping("/")
    public Note update(@RequestBody Note n) {
        return noteService.save(n);
    }*/
    
    @PutMapping("/")
    public Note update(@RequestBody Note note) {
        if (note.getId() == null || !noteService.existsById(note.getId())) {
            throw new RuntimeException("Note non trouvée pour mise à jour");
        }
        return noteService.save(note);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	noteService.delete(id);
    }
}

