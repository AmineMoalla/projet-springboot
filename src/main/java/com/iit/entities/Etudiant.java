package com.iit.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Etudiant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String matricule;

	private String nom;
	private String prenom;
	@Email
	private String email;

	@Column(name = "date_inscription")
	private LocalDate dateInscription;

	// Relation avec Cours (Many-to-Many)
	/*
	 * @ManyToMany(mappedBy = "etudiants") private List<Cours> coursInscrits = new
	 * ArrayList<Cours>();
	 */

	@OneToMany(mappedBy = "etudiant")
	@JsonIgnore
	private Collection<Inscription> inscriptions = new ArrayList<Inscription>();

	@OneToMany(mappedBy = "etudiant")
	@JsonIgnore
	private Collection<Note> notes = new ArrayList<Note>();

	// ----- Constructeurs ----- //

	public Etudiant() {
	}

	public Etudiant(String matricule, String nom, String prenom, String email, LocalDate dateInscription) {
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.dateInscription = dateInscription;
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

	public Collection<Inscription> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(Collection<Inscription> inscriptions) {
		this.inscriptions = inscriptions;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	@Override
	public String toString() {
		return "Etudiant{" + "matricule='" + matricule + '\'' + ", nom='" + nom + '\'' + ", prenom='" + prenom + '\''
				+ ", email='" + email + '\'' + ", dateInscription=" + dateInscription + '}';
	}
}
