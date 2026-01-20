package com.iit.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.entities.Cours;

import java.util.List;
public interface CoursRepository extends JpaRepository<Cours, Long> {
List<Cours> findByFormateurId(Long formateurId);
}
