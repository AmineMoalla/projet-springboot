package com.iit.entities;

import jakarta.persistence.*;

@Entity

public class AffectationCours {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String annee;
	private String semestre;
	private Integer volumeHoraire;



	@ManyToOne
	private Groupe groupe;

	@ManyToOne()
	private Cours cours;


	public AffectationCours() {
	}

	public AffectationCours(Groupe groupe, Cours cours, String annee, String semestre,
			Integer volumeHoraire) {
		super();
		this.groupe = groupe;
		this.cours = cours;
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

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
