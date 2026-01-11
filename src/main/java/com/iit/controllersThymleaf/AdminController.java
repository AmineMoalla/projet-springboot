package com.iit.controllersThymleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iit.repositories.CoursRepository;
import com.iit.repositories.EtudiantRepository;
import com.iit.repositories.FormateurRepository;
import com.iit.repositories.InscriptionRepository;
import com.iit.services.AffectationCoursService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CoursRepository coursRepository;
    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private FormateurRepository formateurRepository;
    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private AffectationCoursService affectationCoursService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long nbCours = coursRepository.count();
        long nbEtudiants = etudiantRepository.count();
        long nbFormateurs = formateurRepository.count();
        model.addAttribute("nbCours", nbCours);
        model.addAttribute("nbEtudiants", nbEtudiants);
        model.addAttribute("nbFormateurs", nbFormateurs);
        // Liste des affectations pour affichage dans le dashboard
        model.addAttribute("affectationList", affectationCoursService.getAll());
        return "dashboard/index";
    }
}