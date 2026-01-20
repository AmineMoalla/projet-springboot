package com.iit.controllersThymleaf;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import com.iit.entities.Formateur;
import com.iit.repositories.FormateurRepository;
import com.iit.services.FormateurService;
import com.iit.entities.Cours;
import com.iit.repositories.ApplicationUserRepository;
import com.iit.security.ApplicationUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.iit.services.SendGridEmailService;

@Controller
@RequestMapping("/admin/formateur")
public class FormateurController {

    @Autowired
    private FormateurService formateurService;

    @Autowired
    private com.iit.services.CoursService coursService;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SendGridEmailService sendGridEmailService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("formateurs", formateurService.getAll());
        return "formateur/index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public String formFormateur(Model model) {
        model.addAttribute("formateur", new Formateur());
        // Filtrer les cours sans formateur
        java.util.List<com.iit.entities.Cours> coursSansFormateur = coursService.getAll().stream()
            .filter(c -> c.getFormateur() == null)
            .toList();
        model.addAttribute("coursList", coursSansFormateur);
        return "formateur/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String save(@Valid Formateur f,
                       BindingResult br,
                       @RequestParam(required = false) Long cours,
                       @RequestParam String email,
                       RedirectAttributes ra) {
        if (br.hasErrors()) return "formateur/form";
        // Vérifier si l'email existe déjà
        java.util.Optional<ApplicationUser> existingUserOpt = userRepository.findByEmail(email);
        String generatedPassword = null;
        ApplicationUser user;
        if (f.getUser() != null) {
            // Modification : ne pas toucher à l'email ni au mot de passe
            user = f.getUser();
        } else {
            // Création : vérifier unicité de l'email
            if (existingUserOpt.isPresent()) {
                br.rejectValue("user.email", "error.user", "Cet email existe déjà.");
                ra.addFlashAttribute("error", "Cet email existe déjà.");
                return "formateur/form";
            }
            user = new ApplicationUser();
            user.setEmail(email);
            generatedPassword = genererMotDePasse(10);
            user.setPassword(passwordEncoder.encode(generatedPassword));
            user.setRole(ApplicationUser.Role.FORMATEUR);
            userRepository.save(user);
        }
        f.setUser(user);
        Cours c = null;
        if (cours != null) {
            c = coursService.getById(cours).orElse(null);
        }
        // 1. Sauvegarder le formateur sans cours pour obtenir son id
        f.setCours(null);
        formateurService.save(f);
        // 2. Associer le formateur au cours et sauvegarder le cours
        if (c != null) {
            c.setFormateur(f);
            coursService.save(c);
            // 3. Associer le cours au formateur et sauvegarder le formateur
            f.setCours(c);
            formateurService.save(f);
        }
        // Envoi de l'email de bienvenue uniquement à la création
        if (generatedPassword != null) {
            try {
                String subject = "Bienvenue sur la plateforme IIT";
                String content = "Bonjour " + f.getNom() + ",<br><br>" +
                    "Votre compte formateur a été créé.<br>" +
                    "Email : <b>" + email + "</b><br>" +
                    "Mot de passe : <b>" + generatedPassword + "</b><br><br>" +
                    "Merci de vous connecter à la plateforme.";
                sendGridEmailService.sendEmail(email, subject, content);
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Formateur créé mais l'email n'a pas pu être envoyé : " + e.getMessage());
                return "redirect:/admin/formateur/index";
            }
            ra.addFlashAttribute("success", "Formateur créé avec succès! Un email a été envoyé.");
        } else {
            ra.addFlashAttribute("success", "Formateur modifié avec succès!");
        }
        return "redirect:/admin/formateur/index";
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


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Formateur formateur = formateurService.getById(id);
        if (formateur == null) {
            throw new RuntimeException("Formateur introuvable");
        }
        // Retourner toute la liste des cours
        java.util.List<com.iit.entities.Cours> tousLesCours = coursService.getAll();
        model.addAttribute("formateur", formateur);
        model.addAttribute("coursList", tousLesCours);
        return "formateur/edit";
        }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String update(@Valid Formateur f,
                        BindingResult br,
                        @RequestParam(required = false) Long cours,
                        @RequestParam String email,
                        @RequestParam Long userId,
                        RedirectAttributes ra) {
        if (br.hasErrors()) return "formateur/edit";
        // Toujours réassocier le user existant
        ApplicationUser user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            ra.addFlashAttribute("error", "Utilisateur associé introuvable");
            return "redirect:/admin/formateur/index";
        }
        f.setUser(user);
        Formateur formateurOrigine = formateurService.getById(f.getId());
        Cours coursOrigine = formateurOrigine != null ? formateurOrigine.getCours() : null;
        if (cours != null) {
            Cours c = coursService.getById(cours).orElse(null);
            // Si le cours a changé, mettre à jour l'ancien cours
            if (coursOrigine != null && (c == null || !coursOrigine.getId().equals(c.getId()))) {
                coursOrigine.setFormateur(null);
                coursService.save(coursOrigine);
            }
            // Associer le nouveau cours au formateur
            f.setCours(c);
            if (c != null) {
                c.setFormateur(f);
                coursService.save(c);
            }
        } else {
            // Si aucun cours sélectionné, conserver le cours existant
            f.setCours(coursOrigine);
        }
        formateurService.save(f);
        ra.addFlashAttribute("success", "Formateur mis à jour avec succès!");
        return "redirect:/admin/formateur/index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        if (id != null && formateurService.existsById(id)) {
            Formateur formateur = formateurService.getById(id);
            if (formateur != null && formateur.getCours() != null) {
                Cours cours = formateur.getCours();
                cours.setFormateur(null);
                coursService.save(cours);
            }
            formateurService.delete(id);
            ra.addFlashAttribute("success", "Formateur supprimé avec succès!");
        } else {
            ra.addFlashAttribute("error", "Formateur non trouvé avec l'ID: " + id);
        }
        return "redirect:/admin/formateur/index";
    }
}

