package com.iit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iit.entities.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
