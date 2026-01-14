package com.iit.controllersThymleaf;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;  
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import com.iit.entities.AffectationCours;
import com.iit.repositories.AffectationRepository;
import com.iit.services.AffectationCoursService;
import com.iit.repositories.GroupeRepository;
import com.iit.repositories.CoursRepository;

@Controller
@RequestMapping("/admin/affectation")
public class AffectationCoursController {

    @Autowired
    private AffectationCoursService affectationService;

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private CoursRepository coursRepository;

    @GetMapping("/index")
    public String index(Model model)
    {
        model.addAttribute("affectationList", affectationService.getAll());


        return "affectation/index"; 
    }

    @GetMapping("/form")
    public String formAffectation(Model model) {
        model.addAttribute("affectation", new AffectationCours());
        model.addAttribute("groupes", groupeRepository.findAll());
        model.addAttribute("cours", coursRepository.findAll());
        return "affectation/form";
    }

    @PostMapping("/save")
    public String save(@Valid AffectationCours affectation, BindingResult bindingResult, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return "affectation/form";
        }
        affectationService.save(affectation);
        ra.addFlashAttribute("success", "Affectation créée avec succès!");
        return "redirect:/admin/affectation/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        affectationService.delete(id);
        ra.addFlashAttribute("success", "Affectation supprimée avec succès!");
        return "redirect:/admin/affectation/index";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(name="id") Long id) {
        AffectationCours a = affectationService.getById(id).orElse(null);
        model.addAttribute("affectation", a);
        model.addAttribute("groupes", groupeRepository.findAll());
        model.addAttribute("cours", coursRepository.findAll());
        return "affectation/edit";
    }

    @PostMapping("/update")
    public String update(@Valid AffectationCours affectation, BindingResult bindingResult, Model model, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupes", groupeRepository.findAll());
            model.addAttribute("cours", coursRepository.findAll());
            return "affectation/edit";
        }
        affectationService.save(affectation);
        ra.addFlashAttribute("success", "Affectation mise à jour avec succès!");
        return "redirect:/admin/affectation/index";
    }
}
