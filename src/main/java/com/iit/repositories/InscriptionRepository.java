package com.iit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List ;

import com.iit.entities.Inscription;
@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
	// Trouver les inscriptions par groupe (null = sans groupe)
	List<Inscription> findByGroupe_Id(Long groupeId);
	List<Inscription> findByGroupeIsNull();
    long countByGroupeId(Long groupeId);
}
