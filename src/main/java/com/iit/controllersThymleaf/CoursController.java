package com.iit.controllersThymleaf;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iit.entities.Cours;
import com.iit.services.CoursService;

@Controller
@RequestMapping("/admin/cours")
public class CoursController {

    @Autowired
    private CoursService coursService;

    @Autowired
    private com.iit.services.FormateurService formateurService;

    // Liste de tous les cours
    @GetMapping
    public String index(Model model) {
        model.addAttribute("coursList", coursService.getAll());
        return "cours/index";
    }

    // Formulaire pour créer un nouveau cours
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("cours", new Cours());
        model.addAttribute("formateurList", formateurService.getAll());
        return "cours/form";
    }

    // Formulaire pour éditer un cours existant
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Optional<Cours> coursOptional = coursService.getById(id);
        if (coursOptional.isPresent()) {
            model.addAttribute("cours", coursOptional.get());
            model.addAttribute("formateurList", formateurService.getAll());
            return "cours/form"; // réutilisation du même template form.html
        } else {
            ra.addFlashAttribute("error", "Cours non trouvé avec l'ID: " + id);
            return "redirect:/admin/cours";
        }
    }

    // Création d'un nouveau cours
    @PostMapping
    public String save(@RequestParam String nom,
                       @RequestParam(required = false) Long formateur,
                       RedirectAttributes ra) {
        Cours cours = new Cours();
        cours.setNom(nom);
        if (formateur != null) {
            com.iit.entities.Formateur f = formateurService.getById(formateur);
            cours.setFormateur(f);
        }
        coursService.save(cours);
        ra.addFlashAttribute("success", "Cours créé avec succès!");
        return "redirect:/admin/cours";
    }

    // Mise à jour d'un cours existant
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String nom,
                         @RequestParam(required = false) Long formateur,
                         RedirectAttributes ra) {
        Optional<Cours> coursOptional = coursService.getById(id);
        if (coursOptional.isPresent()) {
            Cours cours = coursOptional.get();
            cours.setNom(nom);
            if (formateur != null) {
                com.iit.entities.Formateur f = formateurService.getById(formateur);
                cours.setFormateur(f);
            } else {
                cours.setFormateur(null);
            }
            coursService.save(cours);
            ra.addFlashAttribute("success", "Cours mis à jour avec succès!");
        } else {
            ra.addFlashAttribute("error", "Cours non trouvé avec l'ID: " + id);
        }
        return "redirect:/admin/cours";
    }
 
    // Suppression d'un cours
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        Optional<Cours> coursOptional = coursService.getById(id);
        if (coursOptional.isPresent()) {
            coursService.delete(id);
            ra.addFlashAttribute("success", "Cours supprimé avec succès!");
        } else {
            ra.addFlashAttribute("error", "Cours non trouvé avec l'ID: " + id);
        }
        return "redirect:/admin/cours";
    }
}
