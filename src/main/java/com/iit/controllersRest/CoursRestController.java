package com.iit.controllersRest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Cours;
import com.iit.repositories.CoursRepository;

@RestController
@RequestMapping("/api/cours")
public class CoursRestController {

    @Autowired
    private CoursRepository coursRepos;

    @GetMapping("/")
    public List<Cours> getAll() {
        return coursRepos.findAll();
    }

    @GetMapping("/{id}")
    public Cours getById(@PathVariable Long id) {
        return coursRepos.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Cours save(@RequestBody Cours c) {
        return coursRepos.save(c);
    }

    @PutMapping("/")
    public Cours update(@RequestBody Cours c) {
        return coursRepos.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        coursRepos.deleteById(id);
    }
}
