package com.iit.controllersThymleaf;


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

@Controller
@RequestMapping("/admin/formateur")
public class FormateurController {

    @Autowired
    private FormateurService formateurService;

    @Autowired
    private com.iit.services.CoursService coursService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("formateurs", formateurService.getAll());
        return "formateur/index";
    }

    @GetMapping("/form")
    public String formFormateur(Model model) {
        model.addAttribute("formateur", new Formateur());
        model.addAttribute("coursList", coursService.getAll());
        return "formateur/form";
    }

    @PostMapping("/save")
    public String save(@Valid Formateur f,
                       BindingResult br,
                       @RequestParam(required = false) Long cours) {
        if (br.hasErrors()) return "formateur/form";
        if (cours != null) {
            com.iit.entities.Cours c = coursService.getById(cours).orElse(null);
            f.setCours(c);
        }
        formateurService.save(f);
        return "redirect:/admin/formateur/index";
    }


        @GetMapping("/edit/{id}")
        public String edit(@PathVariable Long id, Model model) {
            Formateur formateur = formateurService.getById(id);
            if (formateur == null) {
                throw new RuntimeException("Formateur introuvable");
            }
            model.addAttribute("formateur", formateur);
            model.addAttribute("coursList", coursService.getAll());
            return "formateur/edit";
        }


    @PostMapping("/update")
    public String update(@Valid Formateur f,
                        BindingResult br,
                        @RequestParam(required = false) Long cours) {
        if (br.hasErrors()) return "formateur/edit";
        if (cours != null) {
            com.iit.entities.Cours c = coursService.getById(cours).orElse(null);
            f.setCours(c);
        } else {
            f.setCours(null);
        }
        formateurService.save(f);
        return "redirect:/admin/formateur/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        if (id != null && formateurService.existsById(id)) {
            formateurService.delete(id);
            ra.addFlashAttribute("success", "Formateur supprimé avec succès!");
        } else {
            ra.addFlashAttribute("error", "Formateur non trouvé avec l'ID: " + id);
        }
        return "redirect:/admin/formateur/index";
    }
}

