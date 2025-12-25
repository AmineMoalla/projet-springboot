package com.iit.entities;

import jakarta.persistence.*;

@Entity

public class AffectationCours {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Groupe groupe;

	@ManyToOne()
	private Cours cours;

	@ManyToOne()
	private Formateur formateur;

	@Column(nullable = false)
	private String annee;

	private String semestre;
	private Integer volumeHoraire;

	public AffectationCours() {
	}

	public AffectationCours(Groupe groupe, Cours cours, Formateur formateur, String annee, String semestre,
			Integer volumeHoraire) {
		super();
		this.groupe = groupe;
		this.cours = cours;
		this.formateur = formateur;
		this.annee = annee;
		this.semestre = semestre;
		this.volumeHoraire = volumeHoraire;
	}

	public Long getId() {
		return id;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}

	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public Integer getVolumeHoraire() {
		return volumeHoraire;
	}

	public void setVolumeHoraire(Integer volumeHoraire) {
		this.volumeHoraire = volumeHoraire;
	}
}
