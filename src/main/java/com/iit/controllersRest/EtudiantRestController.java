package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Etudiant;
import com.iit.repositories.EtudiantRepository;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantRestController {

    @Autowired
    private EtudiantRepository etudiantRepos;

    @GetMapping("/")
    public List<Etudiant> getAll() {
        return etudiantRepos.findAll();
    }

    @GetMapping("/{id}")
    public Etudiant getById(@PathVariable Long id) {
        return etudiantRepos.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Etudiant save(@RequestBody Etudiant e) {
        return etudiantRepos.save(e);
    }

    @PutMapping("/")
    public Etudiant update(@RequestBody Etudiant e) {
        return etudiantRepos.save(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        etudiantRepos.deleteById(id);
    }
}
