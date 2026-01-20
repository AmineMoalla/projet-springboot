package com.iit.services;

import com.iit.entities.Formateur;
import com.iit.repositories.FormateurRepository;
import org.springframework.stereotype.Service;
import com.iit.repositories.*;

import java.util.List;
import java.util.Optional;
import com.iit.entities.*;
import java.util.Map;
import com.iit.dtos.*;
import java.util.ArrayList;
import java.util.HashMap;
@Service
public class FormateurService {

    private final FormateurRepository formateurRepository;

    private final EtudiantRepository etudiantRepository;
private final CoursRepository coursRepository;
private final NoteRepository noteRepository;
private final AffectationRepository affectationRepository;

   public FormateurService(FormateurRepository formateurRepository,
                        EtudiantRepository etudiantRepository,
                        CoursRepository coursRepository,
                        NoteRepository noteRepository,
                        AffectationRepository affectationRepository) {
    this.formateurRepository = formateurRepository;
    this.etudiantRepository = etudiantRepository;
    this.coursRepository = coursRepository;
    this.noteRepository = noteRepository;
    this.affectationRepository = affectationRepository;
}


    public List<Formateur> getAll() {
        return formateurRepository.findAll();
    }

    public Formateur getById(Long id) {
        return formateurRepository.findById(id).orElse(null);
    }

    public Formateur save(Formateur formateur) {
        return formateurRepository.save(formateur);
    }
   
    public void delete(Long id) {
        formateurRepository.deleteById(id);
    }
    public boolean existsById(Long id) {
        return formateurRepository.existsById(id);
    }

      public Cours getCoursByFormateurId(Long formateurId) {
        Formateur f = formateurRepository.findById(formateurId).orElse(null);
        return f != null ? f.getCours() : null;
    }

      public List<EtudiantWithNotesDTO> getEtudiantsAvecNotes(Long formateurId) {
        Formateur formateur = formateurRepository.findById(formateurId)
                .orElseThrow(() -> new RuntimeException("Formateur non trouvé"));

        Cours cours = formateur.getCours();

        List<AffectationCours> affectations = affectationRepository.findByCours(cours);

        List<EtudiantWithNotesDTO> dtos = new ArrayList<>();

        for (AffectationCours agc : affectations) {
            Groupe groupe = agc.getGroupe();
            List<Etudiant> etudiants = etudiantRepository.findByInscriptionGroupe(groupe);

            for (Etudiant e : etudiants) {
                Note note = e.getNotes().stream()
                        .filter(n -> n.getCours().getId().equals(cours.getId()))
                        .findFirst()
                        .orElse(null);

                List<EtudiantWithNotesDTO.NoteDTO> notesDTO = new ArrayList<>();
                if (note != null) {
                    notesDTO.add(new EtudiantWithNotesDTO.NoteDTO(
                            note.getId(),
                            note.getValeur(),
                            note.getCours().getNom()
                    ));
                }

                dtos.add(new EtudiantWithNotesDTO(
                        e.getId(),
                        e.getMatricule(),
                        e.getNom(),
                        e.getPrenom(),
                                e.getUser().getEmail(), // ✅ CORRECT

                        e.getDateInscription(),
                        notesDTO,
                        groupe.getId(),   // ID du groupe
                        groupe.getCode()  
                ));
            }
        }

        return dtos;
    }


    // Assignation ou modification de note
    public Note assignerNote(Long etudiantId, Long coursId, Double valeur) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));

        Optional<Note> optNote = noteRepository.findByEtudiantAndCours(etudiant, cours);

        Note note;
        if (optNote.isPresent()) {
            note = optNote.get();
            note.setValeur(valeur);
        } else {
            note = new Note();
            note.setEtudiant(etudiant);
            note.setCours(cours);
            note.setValeur(valeur);
        }

        return noteRepository.save(note);
    }
    public List<Map<String, Object>> getCoursFormateur(Long formateurId) {
        List<Cours> coursList = coursRepository.findByFormateurId(formateurId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Cours cours : coursList) {
            List<AffectationCours> affectations = affectationRepository.findByCoursId(cours.getId());

            for (AffectationCours aff : affectations) {
                Map<String, Object> map = new HashMap<>();
                map.put("coursNom", cours.getNom());
                map.put("volumeHoraire", aff.getVolumeHoraire());
                map.put("annee", aff.getAnnee());
                map.put("semestre", aff.getSemestre());

                // nombre d'étudiants dans le groupe
                int nombreEtudiants = aff.getGroupe().getInscriptions().size();
                map.put("nombreEtudiants", nombreEtudiants);

                result.add(map);
            }
        }
        return result;
    }

}
