package com.iit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.entities.AffectationCours;

public interface AffectationRepository extends JpaRepository<AffectationCours, Long> {

}
