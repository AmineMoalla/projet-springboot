package com.iit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iit.entities.Inscription;
@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

}
