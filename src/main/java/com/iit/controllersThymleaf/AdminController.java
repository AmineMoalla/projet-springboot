package com.iit.controllersThymleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iit.repositories.CoursRepository;
import com.iit.repositories.EtudiantRepository;
import com.iit.repositories.FormateurRepository;
import com.iit.repositories.GroupeRepository;
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
    private GroupeRepository groupeRepository;

    @Autowired
    private AffectationCoursService affectationCoursService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long nbCours = coursRepository.count();
        long nbEtudiants = etudiantRepository.count();
        long nbFormateurs = formateurRepository.count();
        long nbInscriptions = inscriptionRepository.count();
        model.addAttribute("nbCours", nbCours);
        model.addAttribute("nbEtudiants", nbEtudiants);
        model.addAttribute("nbFormateurs", nbFormateurs);
        model.addAttribute("nbInscriptions", nbInscriptions);
        // Liste des affectations pour affichage dans le dashboard
        model.addAttribute("affectationList", affectationCoursService.getAll());
        // Générer dynamiquement le nombre d'inscriptions par mois pour l'année en cours
        java.time.Year currentYear = java.time.Year.now();
        int[] inscriptionParMois = new int[12];
        java.util.List<com.iit.entities.Inscription> inscriptions = inscriptionRepository.findAll();
        for (com.iit.entities.Inscription insc : inscriptions) {
            if (insc.getDate() != null && java.time.Year.from(insc.getDate()).equals(currentYear)) {
                int month = insc.getDate().getMonthValue();
                inscriptionParMois[month - 1]++;
            }
        }
        model.addAttribute("inscriptionParMois", inscriptionParMois);
        // Préparer les données pour l'histogramme : nombre de cours par groupe
        java.util.List<com.iit.entities.Groupe> groupes = groupeRepository.findAll();
        java.util.List<String> groupeLabels = new java.util.ArrayList<>();
        java.util.List<Integer> coursParGroupe = new java.util.ArrayList<>();
        for (com.iit.entities.Groupe groupe : groupes) {
            groupeLabels.add(groupe.getCode());
            int nbCoursGroupe = (groupe.getAffectationsCours() != null) ? groupe.getAffectationsCours().size() : 0;
            coursParGroupe.add(nbCoursGroupe);
        }
        model.addAttribute("groupeLabels", groupeLabels);
        model.addAttribute("coursParGroupe", coursParGroupe);
        return "dashboard/index";
    }
}