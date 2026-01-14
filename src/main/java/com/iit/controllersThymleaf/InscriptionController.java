package com.iit.controllersThymleaf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Inscription;
import com.iit.entities.Specialite;
import com.iit.repositories.InscriptionRepository;
import com.iit.services.InscriptionService;
import com.iit.services.EtudiantService;
import com.iit.services.GroupeService;

@Controller
@RequestMapping("/admin/inscription")
public class InscriptionController {

      @Autowired
    private InscriptionService inscriptionService;

 @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private GroupeService groupeService;


    @GetMapping("/filtre")
    public String filtreParGroupe(@RequestParam(required = false) String groupeId, Model model) {
        if (groupeId == null || "__all".equals(groupeId)) {
            model.addAttribute("inscriptions", inscriptionService.getAll());
            model.addAttribute("selectedGroupeId", "__all");
        } else if ("__null".equals(groupeId)) {
            model.addAttribute("inscriptions", inscriptionService.getSansGroupe());
            model.addAttribute("selectedGroupeId", null);
        } else {
            try {
                Long gid = Long.valueOf(groupeId);
                model.addAttribute("inscriptions", inscriptionService.getByGroupeId(gid));
                model.addAttribute("selectedGroupeId", gid);
            } catch (NumberFormatException e) {
                model.addAttribute("inscriptions", inscriptionService.getAll());
                model.addAttribute("selectedGroupeId", "__all");
            }
        }
        model.addAttribute("groupes", groupeService.getAll());
        return "inscription/index";
    }

   
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

  

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("inscriptions", inscriptionService.getAll());
        model.addAttribute("groupes", groupeService.getAll());
        model.addAttribute("specialites", Specialite.values());
        return "inscription/index";
    }

    @GetMapping("/form")
    public String formInscription(Model model) {
        model.addAttribute("inscription", new Inscription());
        return "inscription/form";
    }

        @PostMapping("/inscrire")
    public String inscrire(@Valid Inscription i, BindingResult br, Model model) {
        if (br.hasErrors()) return "inscription/valideInscription";
        try {
            inscriptionService.inscrireEtudiant(i);
        } catch (RuntimeException e) {
            model.addAttribute("alerteCapacite", e.getMessage());
            model.addAttribute("inscription", i);
            model.addAttribute("groupes", groupeService.getAll());
            return "inscription/valideInscription";
        }
        return "confirmation";
    }

    @PostMapping("/save")
    public String save(@Valid Inscription i, BindingResult br, Model model) {
        if (br.hasErrors()) return "inscription/form";
        try {
            inscriptionService.save(i);
        } catch (RuntimeException e) {
            model.addAttribute("alerteCapacite", e.getMessage());
            model.addAttribute("inscription", i);
            model.addAttribute("groupes", groupeService.getAll());
            return "inscription/form";
        }
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

