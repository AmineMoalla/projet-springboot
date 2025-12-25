package com.iit.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @OneToOne
    private Inscription inscription;

   
    private String titre;

    @Column(nullable = false, length = 500)
    private String message;


    @Column(nullable = false)
    private LocalDateTime dateCreation;

    
    public Notification() {
        this.dateCreation = LocalDateTime.now();
    }

    public Notification(Inscription inscription, String titre, String message) {
        this.inscription = inscription;
        this.titre = titre;
        this.message = message;
        this.dateCreation = LocalDateTime.now();
       
    }

   
    public Long getId() {
        return id;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
}
