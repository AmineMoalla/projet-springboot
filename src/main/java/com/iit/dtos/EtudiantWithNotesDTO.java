package com.iit.dtos;

import java.time.LocalDate;
import java.util.List;

public class EtudiantWithNotesDTO {

    private Long id;
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateInscription;
    private List<NoteDTO> notes;
    private Long groupeId;
    private String groupeCode;

    public EtudiantWithNotesDTO() {}

    public EtudiantWithNotesDTO(Long id, String matricule, String nom, String prenom,
                                String email, LocalDate dateInscription,
                                List<NoteDTO> notes, Long groupeId, String groupeCode) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateInscription = dateInscription;
        this.notes = notes;
        this.groupeId = groupeId;
        this.groupeCode = groupeCode;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public List<NoteDTO> getNotes() { return notes; }
    public void setNotes(List<NoteDTO> notes) { this.notes = notes; }

    public Long getGroupeId() { return groupeId; }
    public void setGroupeId(Long groupeId) { this.groupeId = groupeId; }

    public String getGroupeCode() { return groupeCode; }
    public void setGroupeCode(String groupeCode) { this.groupeCode = groupeCode; }

    // ✅ Classe interne pour Note
    public static class NoteDTO {
        private Long id;
        private Double valeur;
        private String coursNom;

        public NoteDTO() {}

        public NoteDTO(Long id, Double valeur, String coursNom) {
            this.id = id;
            this.valeur = valeur;
            this.coursNom = coursNom;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public Double getValeur() { return valeur; }
        public void setValeur(Double valeur) { this.valeur = valeur; }

        public String getCoursNom() { return coursNom; }
        public void setCoursNom(String coursNom) { this.coursNom = coursNom; }
    }
}
