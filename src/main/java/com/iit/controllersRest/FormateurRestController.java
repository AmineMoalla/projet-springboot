package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Formateur;
import com.iit.repositories.FormateurRepository;
import com.iit.services.FormateurService;

@RestController
@RequestMapping("/api/formateurs")
public class FormateurRestController {

    @Autowired
    private FormateurService formateurService;

    @GetMapping("/")
    public List<Formateur> getAll() {
        return formateurService.getAll();
    } 

    @GetMapping("/{id}")
    public Formateur getById(@PathVariable Long id) {
        return formateurService.getById(id).orElse(null);
    }

    @PostMapping("/")
    public Formateur save(@RequestBody Formateur f) {
        return formateurService.save(f);
    }

    /*@PutMapping("/")
    public Formateur update(@RequestBody Formateur f) {
        return formateurService.save(f);
    }*/
    
    @PutMapping("/")
    public Formateur update(@RequestBody Formateur formateur) {
        if (formateur.getId() == null ||
            !formateurService.existsById(formateur.getId())) {
            throw new RuntimeException("Formateur non trouvé");
        }
        return formateurService.save(formateur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	formateurService.delete(id);
    }
}
