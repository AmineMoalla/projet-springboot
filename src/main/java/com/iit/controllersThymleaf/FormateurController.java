package com.iit.controllersThymleaf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Formateur;
import com.iit.repositories.FormateurRepository;

@Controller
@RequestMapping("/formateur")
public class FormateurController {

    @Autowired
    private FormateurRepository formateurRepos;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("formateurs", formateurRepos.findAll());
        return "formateurs";
    }

    @GetMapping("/form")
    public String formFormateur(Model model) {
        model.addAttribute("formateur", new Formateur());
        return "formFormateur";
    }

    @PostMapping("/save")
    public String save(@Valid Formateur f, BindingResult br) {
        if (br.hasErrors()) return "formFormateur";
        formateurRepos.save(f);
        return "confirmation";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Formateur f = formateurRepos.findById(id).orElse(null);
        model.addAttribute("formateur", f);
        return "editFormateur";
    }

    @PostMapping("/update")
    public String update(@Valid Formateur f, BindingResult br) {
        if (br.hasErrors()) return "editFormateur";
        formateurRepos.save(f);
        return "confirmation";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        formateurRepos.deleteById(id);
        return "redirect:index";
    }
}
