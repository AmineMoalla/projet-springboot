// package com.iit.controllersRest;

// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import com.iit.entities.Cours;
// import com.iit.repositories.CoursRepository;
// import com.iit.services.CoursService;

// @RestController
// @RequestMapping("/api/cours")  
// public class CoursRestController {

//     @Autowired
//     private CoursService coursService;

//     @GetMapping("/")
//     public List<Cours> getAll() {
//         return coursService.getAll();
//     }

//     @GetMapping("/{id}")
//     public Cours getById(@PathVariable Long id) {
//         return coursService.getById(id).orElse(null);
//     }

//     @PostMapping("/")
//     public Cours save(@RequestBody Cours c) {
//         return coursService.save(c);
//     }

//     /*@PutMapping("/")
//     public Cours update(@RequestBody Cours c) {
//         return coursRepos.save(c);
//     }*/
    
//     @PutMapping("/")
//     public Cours update(@RequestBody Cours c) {
//         if (c.getId() == null || !coursService.existsById(c.getId())) {
//             throw new RuntimeException("Cours non trouvé pour mise à jour");
//         }
//         return coursService.save(c);
//     }

//     @DeleteMapping("/{id}")
//     public void delete(@PathVariable Long id) {
//         coursService.delete(id);
//     }
// }

package com.iit.controllersRest;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.iit.controllersThymleaf.AffectationCoursController;
import com.iit.entities.Cours;
import com.iit.services.CoursService;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cours")  
public class CoursRestController {

    private final AffectationCoursRestController affectationCoursRestController;

    private final AffectationCoursController affectationCoursController;

    @Autowired
    private CoursService coursService;

    CoursRestController(AffectationCoursController affectationCoursController, AffectationCoursRestController affectationCoursRestController) {
        this.affectationCoursController = affectationCoursController;
        this.affectationCoursRestController = affectationCoursRestController;
    }

    @GetMapping("/")
    public List<Cours> getAll() {
        return coursService.getAll();
    }

    @GetMapping("/{id}")
    public Cours getById(@PathVariable Long id) {
        return coursService.getById(id).orElse(null);
    }

    @PostMapping("/")
    public Cours save(@RequestBody Cours c) {
        return coursService.save(c);
    }

    /*@PutMapping("/")
    public Cours update(@RequestBody Cours c) {
        return coursRepos.save(c);
    }*/
    
    @PutMapping("/")
    public Cours update(@RequestBody Cours c) {
        if (c.getId() == null || !coursService.existsById(c.getId())) {
            throw new RuntimeException("Cours non trouvé pour mise à jour");
        }
        return coursService.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        coursService.delete(id);
    }
 

     @GetMapping("/formateur/{id}")
    public ResponseEntity<List<Cours>> getCoursByFormateur(@PathVariable Long id) {
        List<Cours> cours = coursService.findByFormateurId(id);
        return ResponseEntity.ok(cours);
    }


}
