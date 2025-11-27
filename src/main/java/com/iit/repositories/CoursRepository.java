package com.iit.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.entities.Cours;
public interface CoursRepository extends JpaRepository<Cours, Long> {

}
