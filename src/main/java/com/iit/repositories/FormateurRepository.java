package com.iit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iit.entities.Formateur;
@Repository
public interface FormateurRepository extends JpaRepository<Formateur, Long> {

}
