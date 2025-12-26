package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Groupe;
import com.iit.repositories.GroupeRepository;
import com.iit.services.GroupeService;

@RestController
@RequestMapping("/api/groupes")
public class GroupeRestController {

    @Autowired
    private GroupeService groupeService;

    @GetMapping("/")
    public List<Groupe> getAll() {
        return groupeService.getAll();
    }

    @GetMapping("/{id}")
    public Groupe getById(@PathVariable Long id) {
        return groupeService.getById(id).orElse(null);
    }

    @PostMapping("/")
    public Groupe save(@RequestBody Groupe g) {
        return groupeService.save(g);
    }

    /*@PutMapping("/")
    public Groupe update(@RequestBody Groupe g) {
        return groupeService.save(g);
    }*/
    
    
    @PutMapping("/")
    public Groupe update(@RequestBody Groupe groupe) {
        if (groupe.getId() == null ||
            !groupeService.existsById(groupe.getId())) {
            throw new RuntimeException("Groupe non trouvé");
        }
        return groupeService.save(groupe);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	groupeService.delete(id);
    }
}

