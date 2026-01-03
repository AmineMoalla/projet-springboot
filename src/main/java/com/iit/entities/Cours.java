package com.iit.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cours {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String nom;


	@OneToMany(mappedBy = "cours")
	@JsonIgnore
	private Collection<AffectationCours> affectations = new ArrayList<AffectationCours>();

	@OneToMany(mappedBy = "cours")
	@JsonIgnore
	private Collection<Note> notes = new ArrayList<Note>();

	@OneToOne()
	private Formateur formateur;

	public Cours() {
	}

	public Cours(String nom, Formateur formateur, Collection<AffectationCours> affectations, Collection<Note> notes) {
		super();
		this.nom = nom;
		this.formateur = formateur;
		this.affectations = affectations;
		this.notes = notes;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	public Collection<AffectationCours> getAffectations() {
		return affectations;
	}

	public void setAffectations(Collection<AffectationCours> affectations) {
		this.affectations = affectations;
	}

	public Collection<Note> getNotes() {
		return notes;
	}

	public void setNotes(Collection<Note> notes) {
		this.notes = notes;
	}

}
