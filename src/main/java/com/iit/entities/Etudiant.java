package com.iit.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iit.security.ApplicationUser;

@Entity
public class Etudiant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)

	private String matricule;
	private String nom;
	private String prenom;
	private String specialite;
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private ApplicationUser user;


	@Column(name = "date_inscription")
	private LocalDate dateInscription;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inscription_id") // clé étrangère dans Etudiant
    private Inscription inscription;
	// @OneToOne(mappedBy = "etudiant")
	// @JsonIgnore
	// @JoinColumn(name = "inscription_id")  // crée la colonne inscription_id dans etudiant
	// // @Column(nullable = true)
	// private Inscription inscription;

	@OneToMany(mappedBy = "etudiant")
	@JsonIgnore
	private Collection<Note> notes = new ArrayList<Note>();


	

	// ----- Constructeurs ----- //

	public Etudiant() {
	}

	public Etudiant(String matricule, String nom, String prenom, String email, LocalDate dateInscription, Inscription inscription, String specialite) {
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		//this.email = email;
		this.dateInscription = dateInscription;
		this.inscription = inscription;
		this.specialite = specialite;
		
	}

	// ----- Getters & Setters ----- //

	
	
	
	public String getMatricule() {
		return matricule;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}

	public Collection<Note> getNotes() {
		return notes;
	}

	public void setNotes(Collection<Note> notes) {
		this.notes = notes;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	 // getters / setters
    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

	/*public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}*/

	public LocalDate getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(LocalDate dateInscription) {
		this.dateInscription = dateInscription;
	}

	/*
	 * public List<Cours> getCoursInscrits() { return coursInscrits; }
	 * 
	 * public void setCoursInscrits(List<Cours> coursInscrits) { this.coursInscrits
	 * = coursInscrits; }
	 */


	public String getSpecialite() {
		return specialite;
	}


	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	@Override
	public String toString() {
		return "Etudiant{" + "matricule='" + matricule + '\'' + ", nom='" + nom + '\'' + ", prenom='" + prenom + '\''
				+ ", email='" + '\'' + ", dateInscription=" + dateInscription + '}';
	}

	// Retourne la liste des cours de l'étudiant via son inscription/groupe
    public List<Cours> getCoursList() {
        List<Cours> coursList = new ArrayList<>();
        if (inscription != null && inscription.getGroupe() != null) {
            Groupe groupe = inscription.getGroupe();
            if (groupe.getAffectationsCours() != null) {
                for (AffectationCours ac : groupe.getAffectationsCours()) {
                    if (ac.getCours() != null) {
                        coursList.add(ac.getCours());
                    }
                }
            }
        }
        return coursList;
    }
}