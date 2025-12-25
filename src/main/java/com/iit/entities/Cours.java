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

	@Column(unique = true)
	private String nom;


	@OneToMany(mappedBy = "cours")
	private Collection<AffectationCours> affectations = new ArrayList<AffectationCours>();

	@OneToMany(mappedBy = "cours")
	private Collection<Note> notes = new ArrayList<Note>();

	public Cours() {
	}

	public Cours(String nom, Formateur formateur, Collection<AffectationCours> affectations, Collection<Note> notes) {
		super();
		this.nom = nom;
		
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
