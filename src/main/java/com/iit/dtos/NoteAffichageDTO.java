package com.iit.dtos;

public class NoteAffichageDTO {
    private String cours;
    private Double note;
    private String formateur;

    public NoteAffichageDTO(String cours, Double note, String formateur) {
        this.cours = cours;
        this.note = note;
        this.formateur = formateur;
    }

    public String getCours() {
        return cours;
    }

    public Double getNote() {
        return note;
    }

    public String getFormateur() {
        return formateur;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public void setFormateur(String formateur) {
        this.formateur = formateur;
    }}