// package com.iit.controllersRest;


// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import com.iit.entities.Formateur;
// import com.iit.repositories.FormateurRepository;
// import com.iit.services.FormateurService;

// @RestController
// @RequestMapping("/api/formateurs")
// public class FormateurRestController {

//     @Autowired
//     private FormateurService formateurService;

//     @GetMapping("/")
//     public List<Formateur> getAll() {
//         return formateurService.getAll();
//     } 

//     @GetMapping("/{id}")
//     public Formateur getById(@PathVariable Long id) {
//         return formateurService.getById(id);
//     }

//     @PostMapping("/")
//     public Formateur save(@RequestBody Formateur f) {
//         return formateurService.save(f);
//     }

//     /*@PutMapping("/")
//     public Formateur update(@RequestBody Formateur f) {
//         return formateurService.save(f);
//     }*/
    
//     @PutMapping("/")
//     public Formateur update(@RequestBody Formateur formateur) {
//         if (formateur.getId() == null ||
//             !formateurService.existsById(formateur.getId())) {
//             throw new RuntimeException("Formateur non trouvé");
//         }
//         return formateurService.save(formateur);
//     }

//     @DeleteMapping("/{id}")
//     public void delete(@PathVariable Long id) {
//     	formateurService.delete(id);
//     }
// }
package com.iit.controllersRest;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Cours;
import com.iit.entities.Formateur;
import com.iit.entities.Etudiant;
import com.iit.entities.Note;
import com.iit.repositories.*;
import com.iit.services.FormateurService;
import com.iit.dtos.EtudiantWithNotesDTO;
import com.iit.dtos.NoteDTO;
import com.iit.entities.Note;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/formateurs")
public class FormateurRestController {

    @Autowired
    private FormateurService formateurService;
    @Autowired
    private  EtudiantRepository etudiantRepository;
    @Autowired
    private CoursRepository coursRepository;
    @Autowired
    private  NoteRepository noteRepository;

    @GetMapping("/")
    public List<Formateur> getAll() {
        return formateurService.getAll();
    } 

    @GetMapping("/{id}")
    public Formateur getById(@PathVariable Long id) {
        return formateurService.getById(id);
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

    @GetMapping("/{id}/cours")
    public Cours getCoursByFormateur(@PathVariable Long id) {
        return formateurService.getCoursByFormateurId(id);
    }


 @GetMapping("/{id}/etudiants-notes")
public List<EtudiantWithNotesDTO> getEtudiantsAvecNotes(@PathVariable Long id) {
    return formateurService.getEtudiantsAvecNotes(id);
}
    
    @GetMapping("/{id}/cours/details")
    public List<Map<String, Object>> getCoursFormateur(@PathVariable Long id) {
        return formateurService.getCoursFormateur(id);
    }
    public NoteDTO saveOrUpdateNote(NoteDTO noteDTO) {

        System.out.println("Requête pour créer/modifier note : " + noteDTO);

        // Vérifier l'étudiant
        Etudiant etudiant = etudiantRepository.findById(noteDTO.getEtudiantId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        // Vérifier le cours
        Cours cours = coursRepository.findById(noteDTO.getCoursId())
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        Note note;
        if(noteDTO.getId() != null) {
            note = noteRepository.findById(noteDTO.getId()).orElse(new Note());
        } else {
            // Vérifier si l'étudiant a déjà une note pour ce cours
            note = noteRepository.findByEtudiantAndCours(etudiant, cours).orElse(new Note());
        }

        note.setEtudiant(etudiant);
        note.setCours(cours);
        note.setValeur(noteDTO.getValeur());

        noteRepository.save(note);

        // Mettre à jour l'ID dans le DTO pour renvoyer la note créée/modifiée
        noteDTO.setId(note.getId());
        return noteDTO;
    }
}
