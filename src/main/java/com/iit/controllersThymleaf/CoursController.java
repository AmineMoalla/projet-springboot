package com.iit.controllersThymleaf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Cours;
import com.iit.repositories.CoursRepository;

@Controller
@RequestMapping("/cours")
public class CoursController {

    @Autowired
    private CoursRepository coursRepos;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("coursList", coursRepos.findAll());
        return "cours"; // templates/cours.html
    }

    @GetMapping("/form")
    public String formCours(Model model) {
        model.addAttribute("cours", new Cours());
        return "formCours";
    }

    @PostMapping("/save")
    public String save(@Valid Cours c, BindingResult br) {
        if (br.hasErrors()) return "formCours";
        coursRepos.save(c);
        return "confirmation";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Cours c = coursRepos.findById(id).orElse(null);
        model.addAttribute("cours", c);
        return "editCours";
    }

    @PostMapping("/update")
    public String update(@Valid Cours c, BindingResult br) {
        if (br.hasErrors()) return "editCours";
        coursRepos.save(c);
        return "confirmation";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        coursRepos.deleteById(id);
        return "redirect:index";
    }
}
