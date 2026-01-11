package com.iit.controllersThymleaf;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;  

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
    public String save(@Valid AffectationCours affectation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "affectation/form";
        }
        affectationService.save(affectation);
        return "redirect:/admin/affectation/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, int page, String motCle) {
        affectationService.delete(id);
        return "redirect:index?page=" + page + "&motCle=" + motCle;
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(name="id") Long id) {
        AffectationCours a = affectationService.getById(id).orElse(null);
        model.addAttribute("affectation", a);
        return "editAffectation";
    }

    @PostMapping("/update")
    public String update(@Valid AffectationCours affectation, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "editAffectation";
        affectationService.save(affectation);
        return "confirmation";
    }
}
