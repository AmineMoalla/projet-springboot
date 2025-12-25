package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Formateur;
import com.iit.repositories.FormateurRepository;

@RestController
@RequestMapping("/api/formateurs")
public class FormateurRestController {

    @Autowired
    private FormateurRepository formateurRepos;

    @GetMapping("/")
    public List<Formateur> getAll() {
        return formateurRepos.findAll();
    }

    @GetMapping("/{id}")
    public Formateur getById(@PathVariable Long id) {
        return formateurRepos.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Formateur save(@RequestBody Formateur f) {
        return formateurRepos.save(f);
    }

    @PutMapping("/")
    public Formateur update(@RequestBody Formateur f) {
        return formateurRepos.save(f);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        formateurRepos.deleteById(id);
    }
}
