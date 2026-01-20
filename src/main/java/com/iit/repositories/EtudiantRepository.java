package com.iit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iit.entities.*;
@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
       Optional<Etudiant> findByUserId(Long userId);
    Optional<Etudiant> findByUserEmail(String email); 

    @Query("SELECT DISTINCT e " +
           "FROM Etudiant e " +
           "JOIN e.inscription i " +
           "JOIN i.groupe g " +
           "JOIN AffectationCours ac ON ac.groupe.id = g.id " +
           "JOIN ac.cours c " +
           "WHERE c.formateur.id = :formateurId")
    List<Etudiant> findEtudiantsByFormateur(@Param("formateurId") Long formateurId);
   //  List<Etudiant> findByGroupe(Groupe groupe);
     List<Etudiant> findByInscriptionGroupe(Groupe groupe);
}

