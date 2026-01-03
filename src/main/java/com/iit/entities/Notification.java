package com.iit.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    
 @OneToOne
    private Inscription inscription;

    @Column(nullable = false, length = 500)
    private String message;


    @Column(nullable = false)
    private LocalDateTime dateCreation;

    
    public Notification() {
       
    }

    public Notification(Inscription inscription, String titre, String message, LocalDateTime dateCreation) {
        this.inscription = inscription;
        this.titre = titre;
        this.message = message;
        this.dateCreation = dateCreation;
       
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
