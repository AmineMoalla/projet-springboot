package com.iit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.entities.Groupe;

public interface GroupeRepository extends JpaRepository<Groupe, Long> {
	java.util.List<Groupe> findBySpecialite(com.iit.entities.Specialite specialite);
	
}
