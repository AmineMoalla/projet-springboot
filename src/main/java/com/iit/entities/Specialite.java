package com.iit.entities;

public enum Specialite {
    Informatique("Informatique"),
   
    Electrique("Electrique"),
    Mecanique("Mecanique");
    

    private final String libelle;

    Specialite(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
