package com.iit.controllersRest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.AffectationCours;
import com.iit.repositories.AffectationRepository;

@RestController
@RequestMapping("/api/affectations")
public class AffectationCoursRestController {

    @Autowired
    private AffectationRepository affectationRepos;

    @GetMapping("/index")
    public String accueil() {
        return "Bienvenue au service REST 'AffectationCours'";
    }

    @GetMapping(value="/", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<AffectationCours> getAll() {
        return affectationRepos.findAll();
    }

    @GetMapping(value="/{id}", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AffectationCours getById(@PathVariable Long id) {
        return affectationRepos.findById(id).orElse(null);
    }

    @PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE,
                 produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AffectationCours save(@RequestBody AffectationCours a) {
        return affectationRepos.save(a);
    }

    @PutMapping(value="/", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AffectationCours update(@RequestBody AffectationCours a) {
        return affectationRepos.save(a);
    }

    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable Long id) {
        affectationRepos.deleteById(id);
    }
}
