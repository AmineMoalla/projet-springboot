package com.iit.controllersThymleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iit.entities.Specialite;
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

    //@PreAuthorize("hasRole('ADMIN')")
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
        
        // Statistiques inscriptions par spécialité
        java.util.List<String> specialiteLabels = new java.util.ArrayList<>();
        java.util.List<Integer> inscriptionsParSpecialite = new java.util.ArrayList<>();
        java.util.List<com.iit.entities.Etudiant> allEtudiants = etudiantRepository.findAll();
        for (Specialite spec : Specialite.values()) {
            specialiteLabels.add(spec.getLibelle());
            int count = 0;
            for (com.iit.entities.Inscription insc : inscriptions) {
                if (insc.getGroupe() != null && insc.getGroupe().getSpecialite() == spec) {
                    count++;
                }
            }
            inscriptionsParSpecialite.add(count);
        }
        model.addAttribute("specialiteLabels", specialiteLabels);
        model.addAttribute("inscriptionsParSpecialite", inscriptionsParSpecialite);
        
        // Statistiques inscriptions par niveau
        java.util.Map<String, Integer> niveauMap = new java.util.LinkedHashMap<>();
        for (com.iit.entities.Inscription insc : inscriptions) {
            String niveau = "Non défini";
            if (insc.getGroupe() != null && insc.getGroupe().getNiveau() != null) {
                niveau = insc.getGroupe().getNiveau();
            }
            niveauMap.put(niveau, niveauMap.getOrDefault(niveau, 0) + 1);
        }
        model.addAttribute("niveauLabels", new java.util.ArrayList<>(niveauMap.keySet()));
        model.addAttribute("inscriptionsParNiveau", new java.util.ArrayList<>(niveauMap.values()));
        
        return "dashboard/index";
    }
}