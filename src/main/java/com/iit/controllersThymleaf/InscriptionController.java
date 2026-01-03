package com.iit.controllersThymleaf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Inscription;
import com.iit.repositories.InscriptionRepository;
import com.iit.services.InscriptionService;

@Controller
@RequestMapping("/admin/inscription")
public class InscriptionController {

    @Autowired
    private com.iit.services.EtudiantService etudiantService;

    @Autowired
    private com.iit.services.GroupeService groupeService;
    @PostMapping("/valider/{id}")
    public String validerInscription(@PathVariable Long id) {
        Inscription inscription = inscriptionService.getById(id).orElse(null);
        if (inscription != null) {
            // Création de l'étudiant à partir des infos de l'inscription
            com.iit.entities.Etudiant etudiant = new com.iit.entities.Etudiant();
            etudiant.setNom(inscription.getEtudiant().getNom());
            etudiant.setPrenom(inscription.getEtudiant().getPrenom());
            etudiant.setEmail(inscription.getEtudiant().getEmail());
            etudiant.setDateInscription(java.time.LocalDate.now());
            etudiant.setInscription(inscription);
            etudiantService.save(etudiant);
            // Affecter le groupe (déjà dans inscription)
            // Marquer l'inscription comme validée
            //inscription.setValide(true);
            inscriptionService.save(inscription);
        }
        return "redirect:/inscription/index";
    }

    @Autowired
    private InscriptionService inscriptionService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("inscriptions", inscriptionService.getAll());
        return "inscription/index";
    }

    @GetMapping("/form")
    public String formInscription(Model model) {
        model.addAttribute("inscription", new Inscription());
        return "inscription/form";
    }

    @PostMapping("/save")
    public String save(@Valid Inscription i, BindingResult br) {
        if (br.hasErrors()) return "inscription/form";
        inscriptionService.save(i);
        return "confirmation";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Inscription i = inscriptionService.getById(id).orElse(null);
        model.addAttribute("inscription", i);
        return "inscription/edit";
    }

    @PostMapping("/update")
    public String update(@Valid Inscription i, BindingResult br) {
        if (br.hasErrors()) return "inscription/edit";
        inscriptionService.save(i);
        return "confirmation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        inscriptionService.delete(id);
        return "redirect:/admin/inscription/index";
    }
}

