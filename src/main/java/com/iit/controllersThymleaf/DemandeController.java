package com.iit.controllersThymleaf;

import com.iit.entities.Etudiant;
import com.iit.entities.Inscription;
import com.iit.services.EtudiantService;
import com.iit.services.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.iit.entities.Groupe;
import com.iit.services.GroupeService;

import com.iit.services.NotificationService;
// import com.iit.services.EmailService;
import com.iit.services.SendGridEmailService;
import com.iit.entities.Notification;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/demande")
public class DemandeController {


    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private NotificationService notificationService;

    // @Autowired
    // private EmailService emailService;
    @Autowired
    private SendGridEmailService sendGridEmailService;

    @GetMapping("/index")
    public String index(@RequestParam(value = "idEtudiant", required = false) Long idEtudiant, Model model) {
        List<Etudiant> etudiantsSansInscription = etudiantService.getAll()
            .stream()
            .filter(e -> e.getInscription() == null)
            .collect(Collectors.toList());
        model.addAttribute("etudiantsSansInscription", etudiantsSansInscription);
        if (idEtudiant != null) {
            Etudiant etudiant = etudiantService.getById(idEtudiant).orElse(null);
            if (etudiant != null && etudiant.getSpecialite() != null) {
                try {
                    com.iit.entities.Specialite specialite = com.iit.entities.Specialite.valueOf(etudiant.getSpecialite());
                    model.addAttribute("groupes", groupeService.getGroupesAvecCapaciteDisponibleParSpecialite(specialite));
                } catch (Exception ex) {
                    model.addAttribute("groupes", groupeService.getGroupesAvecCapaciteDisponibleAll());
                }
            } else {
                model.addAttribute("groupes", groupeService.getGroupesAvecCapaciteDisponibleAll());
            }
        } else {
            model.addAttribute("groupes", groupeService.getGroupesAvecCapaciteDisponibleAll());
        }
        return "Demande/index";
    }

    @PostMapping("/valider")
    public String validerInscription(@RequestParam Long idEtudiant, @RequestParam Long groupeId, Model model, RedirectAttributes ra) {
        Etudiant etudiant = etudiantService.getById(idEtudiant).orElse(null);
        Groupe groupe = groupeService.getById(groupeId).orElse(null);
        if (etudiant != null && groupe != null) {
            Inscription inscription = new Inscription();
            inscription.setDate(LocalDate.now());
            inscription.setEtudiant(etudiant);
            inscription.setGroupe(groupe);
            try {
                // Générer un mot de passe aléatoire
                String generatedPassword = genererMotDePasse(10);
                // Encoder le mot de passe
                org.springframework.security.crypto.password.PasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
                String encodedPassword = encoder.encode(generatedPassword);
                // Assigner le mot de passe à l'utilisateur
                if (etudiant.getUser() != null) {
                    etudiant.getUser().setPassword(encodedPassword);
                }
                inscriptionService.save(inscription);
                etudiant.setInscription(inscription);
                etudiantService.save(etudiant);

                // Envoi email avec le mot de passe généré
                if (etudiant.getUser() != null && !etudiant.getUser().getEmail().isEmpty()) {
                    String subject = "Vos identifiants d'accès";
                    StringBuilder text = new StringBuilder();
                    text.append("Bonjour,\n\n");
                    text.append("Vous avez été inscrit dans le groupe : ").append(groupe.getCode()).append("\n\n");
                    text.append("Voici vos identifiants pour accéder à la plateforme :\n");
                    text.append("Email : ").append(etudiant.getUser().getEmail()).append("\n");
                    text.append("Mot de passe : ").append(generatedPassword).append("\n");
                    text.append("\nMerci de votre confiance.\nL'équipe YFASCHOOL.");
                    try {
                        sendGridEmailService.sendEmail(etudiant.getUser().getEmail(), subject, text.toString());
                    } catch (Exception ex) {
                        ex.printStackTrace(); // Affiche l'erreur dans la console
                        System.err.println("Erreur lors de l'envoi de l'email : " + ex.getMessage());
                    }
                }

                // Ajout notification
                Notification notification = new Notification(
                    inscription,
                    "Inscription validée",
                    "Inscription validée pour le groupe " + groupe.getCode(),
                    LocalDateTime.now()
                );
                notificationService.save(notification);
                ra.addFlashAttribute("success", "Inscription validée avec succès pour " + etudiant.getNom() + " dans le groupe " + groupe.getCode() + "!");
            } catch (RuntimeException e) {
                List<Etudiant> etudiantsSansInscription = etudiantService.getAll()
                    .stream()
                    .filter(et -> et.getInscription() == null)
                    .collect(Collectors.toList());
                model.addAttribute("etudiantsSansInscription", etudiantsSansInscription);
                if (etudiant != null && etudiant.getSpecialite() != null) {
                    try {
                        com.iit.entities.Specialite specialite = com.iit.entities.Specialite.valueOf(etudiant.getSpecialite());
                        model.addAttribute("groupes", groupeService.getGroupesAvecCapaciteDisponibleParSpecialite(specialite));
                    } catch (Exception ex) {
                        model.addAttribute("groupes", groupeService.getGroupesAvecCapaciteDisponibleAll());
                    }
                } else {
                    model.addAttribute("groupes", groupeService.getGroupesAvecCapaciteDisponibleAll());
                }
                model.addAttribute("alerteCapacite", e.getMessage());
                model.addAttribute("etudiantErreur", etudiant); // Pour pré-remplir le modal
                model.addAttribute("groupeErreurId", groupeId); // Pour pré-remplir le groupe sélectionné
                model.addAttribute("showModal", true);
                return "Demande/index";
            }
        }
        return "redirect:/admin/inscription/index";
    }
    // Génère un mot de passe aléatoire de longueur donnée
    private String genererMotDePasse(int longueur) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < longueur; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
