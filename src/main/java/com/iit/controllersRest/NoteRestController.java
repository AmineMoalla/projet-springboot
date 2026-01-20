// package com.iit.controllersRest;


// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import com.iit.entities.Note;
// import com.iit.repositories.NoteRepository;
// import com.iit.services.NoteService;

// @RestController
// @RequestMapping("/api/notes")
// public class NoteRestController {

//     @Autowired
//     private NoteService noteService;

//     @GetMapping("/")
//     public List<Note> getAll() {
//         return noteService.getAll();
//     }

//     @GetMapping("/{id}")
//     public Note getById(@PathVariable Long id) {
//         return noteService.getById(id).orElse(null);
//     }

//     @PostMapping("/")
//     public Note save(@RequestBody Note n) {
//         return noteService.save(n);
//     }

//     /*PutMapping("/")
//     public Note update(@RequestBody Note n) {
//         return noteService.save(n);
//     }*/
    
//     @PutMapping("/")
//     public Note update(@RequestBody Note note) {
//         if (note.getId() == null || !noteService.existsById(note.getId())) {
//             throw new RuntimeException("Note non trouvée pour mise à jour");
//         }
//         return noteService.save(note);
//     }

//     @DeleteMapping("/{id}")
//     public void delete(@PathVariable Long id) {
//     	noteService.delete(id);
//     }
// }

package com.iit.controllersRest;


import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import com.iit.dtos.*;
import com.iit.entities.Note; 
import com.iit.services.NoteService;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteRestController {

    @Autowired
    private NoteService noteService;

 // ✅ ENDPOINT TEXTE AVANT {id}
    @PostMapping("/save-note")
    public ResponseEntity<NoteDTO> saveNote(@RequestBody NoteDTO noteDTO) {
        NoteDTO savedNote = noteService.saveOrUpdateNote(noteDTO);
        return ResponseEntity.ok(savedNote);
    }
    
    @GetMapping("/")
    public List<Note> getAll() {
        return noteService.getAll();
    }

    @GetMapping("/{id}")
    public Note getById(@PathVariable Long id) {
        return noteService.getById(id).orElse(null);
    }

    @PostMapping("/")
    public Note save(@RequestBody Note n) {
        return noteService.save(n);
    }

    /*PutMapping("/")
    public Note update(@RequestBody Note n) {
        return noteService.save(n);
    }*/
    
    @PutMapping("/")
    public Note update(@RequestBody Note note) {
        if (note.getId() == null || !noteService.existsById(note.getId())) {
            throw new RuntimeException("Note non trouvée pour mise à jour");
        }
        return noteService.save(note);
    }
    // @PostMapping("/save")
    // public ResponseEntity<NoteDTO> saveNote(@RequestBody NoteDTO noteDTO) {
    //     System.out.println("Controller: Reçu NoteDTO => " + noteDTO);
    //     NoteDTO savedNote = noteService.saveOrUpdateNote(noteDTO);
    //     System.out.println("Controller: Note sauvegardée => " + savedNote);
    //     return ResponseEntity.ok(savedNote);
    // }
//    @PostMapping("/save-note")
// public ResponseEntity<NoteDTO> saveNote(@RequestBody NoteDTO noteDTO) {
//     NoteDTO savedNote = noteService.saveOrUpdateNote(noteDTO);
//     return ResponseEntity.ok(savedNote);
// }

@GetMapping("/etudiant/{id}")
public List<NoteAffichageDTO> getNotesEtudiant(@PathVariable Long id) {
    return noteService.getNotesEtudiant(id);
}
  
}

