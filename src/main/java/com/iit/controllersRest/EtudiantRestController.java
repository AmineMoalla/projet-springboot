package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Etudiant;
import com.iit.repositories.EtudiantRepository;
import com.iit.services.EtudiantService;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantRestController {

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping("/")
    public List<Etudiant> getAll() {
        return etudiantService.getAll();
    }

    @GetMapping("/{id}")
    public Etudiant getById(@PathVariable Long id) {
        return etudiantService.getById(id).orElse(null);
    }

    @PostMapping("/")
    public Etudiant save(@RequestBody Etudiant e) {
        return etudiantService.save(e);
    }

    /*@PutMapping("/")
    public Etudiant update(@RequestBody Etudiant e) {
        return etudiantRepos.save(e);
    }*/
    
    
    @PutMapping("/")
    public Etudiant update(@RequestBody Etudiant e) {
        if (e.getId() == null || !etudiantService.existsById(e.getId())) {
            throw new RuntimeException("Etudiant non trouvé pour mise à jour");
        }
        return etudiantService.save(e);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	etudiantService.delete(id);
    }
}
