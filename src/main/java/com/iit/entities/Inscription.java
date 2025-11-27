package com.iit.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Inscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;

	@ManyToOne
	private Etudiant etudiant;

	@ManyToOne
	private Cours cours;

	// Constructeurs

	public Inscription() {
	}

	public Inscription(LocalDate date, Etudiant etudiant, Cours cours) {
		this.date = date;
		this.etudiant = etudiant;
		this.cours= cours;
	}

	// Getters & Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	@Override
	public String toString() {
		return "Inscription{" + "id=" + id + ", date=" + date + ", etudiant="
				+ (etudiant != null ? etudiant.getNom() : "Aucun") + ", cours="
				+ (cours!= null ? cours.getTitre() : "Aucun") + '}';
	}

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours= cours;
	}
}
