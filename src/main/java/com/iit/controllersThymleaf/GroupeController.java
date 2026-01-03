package com.iit.controllersThymleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Groupe;
import com.iit.repositories.GroupeRepository;
import com.iit.services.GroupeService;

@Controller
@RequestMapping("/admin/groupe")
public class GroupeController {

    @Autowired
    private GroupeService groupeService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("groupeList", groupeService.getAll());
        return "groupe/index";
    }

    @GetMapping("/new")
    public String formGroupe(Model model) {
        model.addAttribute("groupe", new Groupe());
        return "groupe/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Groupe g, BindingResult br) {
        if (br.hasErrors()) return "groupe/form";
        groupeService.save(g);
        return "redirect:/admin/groupe/index";
    }

    @GetMapping({"/edit/{id}", "/edit"})
    public String edit(Model model, @PathVariable(required = false) Long id, @RequestParam(required = false) Long idParam) {
        Long groupeId = id != null ? id : idParam;
        Groupe g = groupeService.getById(groupeId).orElse(null);
        model.addAttribute("groupe", g);
        return "groupe/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Groupe g, BindingResult br, @RequestParam Long id) {
        if (br.hasErrors()) return "groupe/edit";
        g.setId(id);
        groupeService.save(g);
        return "redirect:/admin/groupe/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        groupeService.delete(id);
        return "redirect:/admin/groupe/index";
    }
}

