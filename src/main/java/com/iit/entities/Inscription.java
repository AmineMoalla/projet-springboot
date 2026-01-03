package com.iit.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Inscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	private boolean valide=false;


	// @OneToOne
	// @Column(nullable = true)
	// @JoinColumn(name = "etudiant_id", nullable = true)
	// @OneToOne(mappedBy = "inscription")
	// private Etudiant etudiant;
   @ManyToOne
    @JoinColumn(name = "etudiant_id") // clé étrangère dans Inscription
    private Etudiant etudiant;

	@ManyToOne
	
	@JoinColumn(name = "groupe_id", nullable = true)

    private Groupe groupe;


	


	public Inscription() {
	}
	public Inscription(LocalDate date, Etudiant etudiant, Groupe groupe) {
		super();
		this.date = date;
		this.etudiant = etudiant;
		this.groupe = groupe;
	}

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
	public boolean isValide() {
		return valide;
	}
	public void setValide(boolean valide) {
		this.valide = valide;
	}

}
