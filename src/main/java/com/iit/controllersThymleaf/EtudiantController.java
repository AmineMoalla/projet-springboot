package com.iit.controllersThymleaf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Etudiant;
import com.iit.repositories.EtudiantRepository;
import com.iit.services.EtudiantService;

@Controller
@RequestMapping("/admin/etudiant")
public class EtudiantController {

    @Autowired
    private com.iit.services.InscriptionService inscriptionService;

    @PostMapping("/valider/{id}")
    public String validerEtudiant(@PathVariable Long id) {
        Etudiant etudiant = etudiantService.getById(id).orElse(null);
        if (etudiant != null) {
            com.iit.entities.Inscription inscription = new com.iit.entities.Inscription();
            inscription.setDate(java.time.LocalDate.now());
            inscription.setEtudiant(etudiant);
            //inscription.setValide(false);
            // Groupe reste vide
            inscriptionService.save(inscription);
        }
        return "redirect:/admin/inscription/index";
    }

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("etudiants", etudiantService.getAll());
        return "etudiant/index"; // templates/etudiants.html
    }

    @GetMapping("/form")
    public String formEtudiant(Model model) {
        model.addAttribute("etudiant", new Etudiant());
        return "etudiant/form"; 
    }

    @PostMapping("/save")
    public String save(@Valid Etudiant etudiant, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "etudiant/form";
        etudiantService.save(etudiant);
        return "confirmation";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Etudiant e = etudiantService.getById(id).orElse(null);
        model.addAttribute("etudiant", e);
        return "etudiant/edit"; // templates/editEtudiant.html
    }

    @PostMapping("/update")
    public String update(@Valid Etudiant etudiant, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "etudiant/edit";
        etudiantService.save(etudiant);
        return "confirmation";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        etudiantService.delete(id);
        return "redirect:index";
    }
}

