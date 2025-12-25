package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Note;
import com.iit.repositories.NoteRepository;

@RestController
@RequestMapping("/api/notes")
public class NoteRestController {

    @Autowired
    private NoteRepository noteRepos;

    @GetMapping("/")
    public List<Note> getAll() {
        return noteRepos.findAll();
    }

    @GetMapping("/{id}")
    public Note getById(@PathVariable Long id) {
        return noteRepos.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Note save(@RequestBody Note n) {
        return noteRepos.save(n);
    }

    @PutMapping("/")
    public Note update(@RequestBody Note n) {
        return noteRepos.save(n);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        noteRepos.deleteById(id);
    }
}

