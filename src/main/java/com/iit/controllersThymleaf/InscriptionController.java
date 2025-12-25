package com.iit.controllersThymleaf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Inscription;
import com.iit.repositories.InscriptionRepository;

@Controller
@RequestMapping("/inscription")
public class InscriptionController {

    @Autowired
    private InscriptionRepository inscriptionRepos;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("inscriptions", inscriptionRepos.findAll());
        return "inscriptions";
    }

    @GetMapping("/form")
    public String formInscription(Model model) {
        model.addAttribute("inscription", new Inscription());
        return "formInscription";
    }

    @PostMapping("/save")
    public String save(@Valid Inscription i, BindingResult br) {
        if (br.hasErrors()) return "formInscription";
        inscriptionRepos.save(i);
        return "confirmation";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Inscription i = inscriptionRepos.findById(id).orElse(null);
        model.addAttribute("inscription", i);
        return "editInscription";
    }

    @PostMapping("/update")
    public String update(@Valid Inscription i, BindingResult br) {
        if (br.hasErrors()) return "editInscription";
        inscriptionRepos.save(i);
        return "confirmation";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        inscriptionRepos.deleteById(id);
        return "redirect:index";
    }
}

