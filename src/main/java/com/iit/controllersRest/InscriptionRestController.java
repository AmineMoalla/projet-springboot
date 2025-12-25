package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Inscription;
import com.iit.repositories.InscriptionRepository;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionRestController {

    @Autowired
    private InscriptionRepository inscriptionRepos;

    @GetMapping("/")
    public List<Inscription> getAll() {
        return inscriptionRepos.findAll();
    }

    @GetMapping("/{id}")
    public Inscription getById(@PathVariable Long id) {
        return inscriptionRepos.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Inscription save(@RequestBody Inscription i) {
        return inscriptionRepos.save(i);
    }

    @PutMapping("/")
    public Inscription update(@RequestBody Inscription i) {
        return inscriptionRepos.save(i);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        inscriptionRepos.deleteById(id);
    }
}

