package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Groupe;
import com.iit.repositories.GroupeRepository;

@RestController
@RequestMapping("/api/groupes")
public class GroupeRestController {

    @Autowired
    private GroupeRepository groupeRepos;

    @GetMapping("/")
    public List<Groupe> getAll() {
        return groupeRepos.findAll();
    }

    @GetMapping("/{id}")
    public Groupe getById(@PathVariable Long id) {
        return groupeRepos.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Groupe save(@RequestBody Groupe g) {
        return groupeRepos.save(g);
    }

    @PutMapping("/")
    public Groupe update(@RequestBody Groupe g) {
        return groupeRepos.save(g);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupeRepos.deleteById(id);
    }
}

