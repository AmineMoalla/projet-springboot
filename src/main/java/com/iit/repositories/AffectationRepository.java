package com.iit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.entities.AffectationCours;
import com.iit.entities.Cours;
import java.util.List;

public interface AffectationRepository extends JpaRepository<AffectationCours, Long> {
 List<AffectationCours> findByCours(Cours cours);
    List<AffectationCours> findByCoursId(Long coursId);
}
