package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Inscription;
import com.iit.repositories.InscriptionRepository;
import com.iit.services.InscriptionService;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionRestController {

    @Autowired
    private InscriptionService inscriptionService;

    @GetMapping("/")
    public List<Inscription> getAll() {
        return inscriptionService.getAll();
    }

    @GetMapping("/{id}")
    public Inscription getById(@PathVariable Long id) {
        return inscriptionService.getById(id).orElse(null);
    }

    @PostMapping("/")
    public Inscription save(@RequestBody Inscription i) {
        return inscriptionService.save(i);
    }

    /*@PutMapping("/")
    public Inscription update(@RequestBody Inscription i) {
        return inscriptionRepos.save(i);
    }*/
    
    
    
    @PutMapping("/")
    public Inscription update(@RequestBody Inscription inscription) {
        if (inscription.getId() == null ||
            !inscriptionService.existsById(inscription.getId())) {
            throw new RuntimeException("Inscription non trouvée");
        }
        return inscriptionService.save(inscription);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	inscriptionService.delete(id);
    }
}

