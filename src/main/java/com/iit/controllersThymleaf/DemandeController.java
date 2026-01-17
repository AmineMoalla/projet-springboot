package com.iit.controllersThymleaf;

import com.iit.entities.Etudiant;
import com.iit.entities.Inscription;
import com.iit.services.EtudiantService;
import com.iit.services.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String index(Model model) {
        List<Etudiant> etudiantsSansInscription = etudiantService.getAll()
            .stream()
            .filter(e -> e.getInscription() == null)
            .collect(Collectors.toList());
        model.addAttribute("etudiantsSansInscription", etudiantsSansInscription);
        model.addAttribute("groupes", groupeService.getAll());
        return "Demande/index";
    }

    @PostMapping("/valider")
    public String validerInscription(@RequestParam Long idEtudiant, @RequestParam Long groupeId, Model model) {
        Etudiant etudiant = etudiantService.getById(idEtudiant).orElse(null);
        Groupe groupe = groupeService.getById(groupeId).orElse(null);
        if (etudiant != null && groupe != null) {
            Inscription inscription = new Inscription();
            inscription.setDate(LocalDate.now());
            inscription.setEtudiant(etudiant);
            inscription.setGroupe(groupe);
            try {
                inscriptionService.save(inscription);
                etudiant.setInscription(inscription);
                etudiantService.save(etudiant);

                // Envoi email
                if (etudiant.getUser() != null && !etudiant.getUser().getEmail().isEmpty()) {
                    String subject = "Confirmation d'inscription";
                    String text = "Bonjour " + etudiant.getNom() + ",\nVotre inscription au groupe " + groupe.getCode() + " a été validée.";
                    try {
                        sendGridEmailService.sendEmail(etudiant.getUser().getEmail(), subject, text);
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
            } catch (RuntimeException e) {
                model.addAttribute("etudiant", etudiant);
                model.addAttribute("groupes", groupeService.getAll());
                model.addAttribute("alerteCapacite", e.getMessage());
                return "inscription/valideInscription";
            }
        }
        return "redirect:/admin/inscription/index";
    }
}
