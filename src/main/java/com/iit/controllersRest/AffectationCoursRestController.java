// package com.iit.controllersRest;


// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
// import org.springframework.web.bind.annotation.*;

// import com.iit.entities.AffectationCours;
// import com.iit.repositories.AffectationRepository;
// import com.iit.services.AffectationCoursService;

// @RestController
// @RequestMapping("/api/affectations")

// public class AffectationCoursRestController {

//     @Autowired
//     private AffectationCoursService affectationService;

//     /*@GetMapping("/index")
//     public String accueil() {
//         return "Bienvenue au service REST 'AffectationCours'";
//     }*/

//     @GetMapping(value="/")
//     public List<AffectationCours> getAll() {
//         return affectationService.getAll();
//     }

//     @GetMapping(value="/{id}")
//     public AffectationCours getById(@PathVariable Long id) {
//         return affectationService.getById(id).orElse(null);
//     }

//     @PostMapping(value="/")
//     public AffectationCours save(@RequestBody AffectationCours a) {
//         return affectationService.save(a);
//     }

//     /*@PutMapping(value="/")
//     public AffectationCours update(@RequestBody AffectationCours a) {
//         return affectationRepos.save(a);
//     }*/
    
//     @PutMapping("/")
//     public AffectationCours update(@RequestBody AffectationCours a) {
//         if (a.getId() == null || !affectationService.existsById(a.getId())) {
//             throw new RuntimeException("Affectation non trouvée");
//         }
//         return affectationService.save(a);
//     }

//     @DeleteMapping(value="/{id}")
//     public void delete(@PathVariable Long id) {
//     	affectationService.delete(id);
//     }
// }
package com.iit.controllersRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.AffectationCours;
import com.iit.services.AffectationCoursService;

@RestController
@RequestMapping("/api/affectations")
@CrossOrigin(origins = "http://localhost:4200") // permet à Angular d'accéder
public class AffectationCoursRestController {

    @Autowired
    private AffectationCoursService affectationService;

    // GET /api/affectations/
    @GetMapping("/")
    public List<AffectationCours> getAll() {
        return affectationService.getAll();
    }

    // GET /api/affectations/{id}
    @GetMapping("/{id}")
    public AffectationCours getById(@PathVariable Long id) {
        return affectationService.getById(id).orElse(null);
    }

    // POST /api/affectations/
    @PostMapping("/")
    public AffectationCours save(@RequestBody AffectationCours a) {
        return affectationService.save(a);
    }

    // PUT /api/affectations/
    @PutMapping("/")
    public AffectationCours update(@RequestBody AffectationCours a) {
        if (a.getId() == null || !affectationService.existsById(a.getId())) {
            throw new RuntimeException("Affectation non trouvée");
        }
        return affectationService.save(a);
    }

    // DELETE /api/affectations/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        affectationService.delete(id);
    }
    
}
