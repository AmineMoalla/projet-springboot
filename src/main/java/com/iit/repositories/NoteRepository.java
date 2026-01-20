package com.iit.repositories;

import com.iit.dtos.NoteAffichageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.iit.entities.Note;
import com.iit.entities.Etudiant;
import com.iit.entities.Cours;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByEtudiantAndCours(Etudiant etudiant, Cours cours);

    @Query("""
    SELECT new com.iit.dtos.NoteAffichageDTO(
        c.nom,
        n.valeur,
        f.nom
    )
    FROM Note n
    JOIN n.cours c
    JOIN c.formateur f
    WHERE n.etudiant.id = :etudiantId
""")
    List<NoteAffichageDTO> findNotesByEtudiant(@Param("etudiantId") Long etudiantId);

}