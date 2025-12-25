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

@Controller
@RequestMapping("/affectation")
public class AffectationCoursController {

    @Autowired
    private AffectationRepository affectationRepos;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name="page", defaultValue="0") int p,
                        @RequestParam(name="motCle", defaultValue="") String mc) {

        Page<AffectationCours> pg = affectationRepos.findAll(PageRequest.of(p, 6));
        int nbrePages = pg.getTotalPages();
        int[] pages = new int[nbrePages];
        for (int i = 0; i < nbrePages; i++) pages[i] = i;

        model.addAttribute("pages", pages);
        model.addAttribute("pageAffectations", pg);
        model.addAttribute("pageCourante", p);
        model.addAttribute("motCle", mc);

        return "affectations"; // JSP/Thymeleaf page à créer : affectations.html
    }

    @GetMapping("/form")
    public String formAffectation(Model model) {
        model.addAttribute("affectation", new AffectationCours());
        return "formAffectation"; // formAffectation.html
    }

    @PostMapping("/save")
    public String save(@Valid AffectationCours affectation, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formAffectation";
        affectationRepos.save(affectation);
        return "confirmation"; // confirmation.html
    }

    @GetMapping("/delete")
    public String delete(Long id, int page, String motCle) {
        affectationRepos.deleteById(id);
        return "redirect:index?page=" + page + "&motCle=" + motCle;
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(name="id") Long id) {
        AffectationCours a = affectationRepos.findById(id).orElse(null);
        model.addAttribute("affectation", a);
        return "editAffectation"; // editAffectation.html
    }

    @PostMapping("/update")
    public String update(@Valid AffectationCours affectation, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "editAffectation";
        affectationRepos.save(affectation);
        return "confirmation";
    }
}
