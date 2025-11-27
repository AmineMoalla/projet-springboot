package com.iit.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Cours {

    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(unique=true)
    private String code;

    private String titre;
    private String description;

    // Many-to-One vers Formateur
    @ManyToOne
   // @JoinColumn(name = "formateur_id")
    private Formateur formateur;
    
    
    @OneToMany(mappedBy = "cours")
    private Collection<Inscription> inscriptions= new ArrayList<Inscription>();
    
    @OneToMany(mappedBy = "cours")
    private Collection<Note>notes= new ArrayList<Note>();

    // Many-to-Many vers Etudiants
   
    /*@JoinTable(
        name = "cours_etudiants",
        joinColumns = @JoinColumn(name = "cours_code"),
        inverseJoinColumns = @JoinColumn(name = "etudiant_matricule")
    )*/
    @ManyToMany(mappedBy="coursInscrits")
    private List<Etudiant> etudiants = new ArrayList<>();

    // Constructeurs

    public Cours() {}

    public Cours(String code, String titre, String description, Formateur formateur) {
        this.code = code;
        this.titre = titre;
        this.description = description;
        this.formateur = formateur;
    }

    // Getters & Setters

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "code='" + code + '\'' +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", formateur=" + (formateur != null ? formateur.getNom() : "Aucun") +
                '}';
    }
}
