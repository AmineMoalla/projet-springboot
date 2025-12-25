package com.iit.controllersThymleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Groupe;
import com.iit.repositories.GroupeRepository;

@Controller
@RequestMapping("/groupe")
public class GroupeController {

    @Autowired
    private GroupeRepository groupeRepos;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("groupes", groupeRepos.findAll());
        return "groupes";
    }

    @GetMapping("/form")
    public String formGroupe(Model model) {
        model.addAttribute("groupe", new Groupe());
        return "formGroupe";
    }

    @PostMapping("/save")
    public String save(@Valid Groupe g, BindingResult br) {
        if (br.hasErrors()) return "formGroupe";
        groupeRepos.save(g);
        return "confirmation";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Groupe g = groupeRepos.findById(id).orElse(null);
        model.addAttribute("groupe", g);
        return "editGroupe";
    }

    @PostMapping("/update")
    public String update(@Valid Groupe g, BindingResult br) {
        if (br.hasErrors()) return "editGroupe";
        groupeRepos.save(g);
        return "confirmation";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        groupeRepos.deleteById(id);
        return "redirect:index";
    }
}

