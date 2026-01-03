package com.iit.entities;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private String nom;
    private String niveau;
    
    @Column(unique=true)
    private String code;
    
       
    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inscription> inscriptions;
    
    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AffectationCours> affectationsCours;

    
    public Groupe() {}

   

    public Groupe( String niveau, String code, List<AffectationCours> affectationsCours, List<Inscription> inscriptions) {
        super();        
        this.niveau = niveau;
        this.code = code;
        this.affectationsCours = affectationsCours;
        this.inscriptions = inscriptions;
    }
   public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }
    public Long getId() {
        return id;
    }

  

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<AffectationCours> getAffectationsCours() {
		return affectationsCours;
	}

	public void setAffectationsCours(List<AffectationCours> affectationsCours) {
		this.affectationsCours = affectationsCours;
	}

	public void setId(Long id) {
		this.id = id;
	}



   }
