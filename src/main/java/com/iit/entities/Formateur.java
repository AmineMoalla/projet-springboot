package com.iit.entities;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.*;

@Entity
public class Formateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nom;
	private String specialite;
	private String email;

	@OneToOne()
	private Cours cours;
	
	public Formateur() {
	}

	public Formateur(String nom, String specialite, String email, Cours cours) {
		this.nom = nom;
		this.specialite = specialite;
		this.email = email;
		this.cours = cours;
	}

	// Getters & Setters

 public Cours getCours() {
		return cours;
	}
	public void setCours(Cours cours) {
		this.cours = cours;
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

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Formateur{" + "id=" + id + ", nom='" + nom + '\'' + ", specialite='" + specialite + '\'' + ", email='"
				+ email + '\'' + '}';
	}
}
