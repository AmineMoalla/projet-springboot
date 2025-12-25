package com.iit.controllersThymleaf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Etudiant;
import com.iit.repositories.EtudiantRepository;

@Controller
@RequestMapping("/etudiant")
public class EtudiantController {

    @Autowired
    private EtudiantRepository etudiantRepos;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("etudiants", etudiantRepos.findAll());
        return "etudiants"; // templates/etudiants.html
    }

    @GetMapping("/form")
    public String formEtudiant(Model model) {
        model.addAttribute("etudiant", new Etudiant());
        return "formEtudiant"; // templates/formEtudiant.html
    }

    @PostMapping("/save")
    public String save(@Valid Etudiant etudiant, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formEtudiant";
        etudiantRepos.save(etudiant);
        return "confirmation";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Etudiant e = etudiantRepos.findById(id).orElse(null);
        model.addAttribute("etudiant", e);
        return "editEtudiant"; // templates/editEtudiant.html
    }

    @PostMapping("/update")
    public String update(@Valid Etudiant etudiant, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "editEtudiant";
        etudiantRepos.save(etudiant);
        return "confirmation";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        etudiantRepos.deleteById(id);
        return "redirect:index";
    }
}

