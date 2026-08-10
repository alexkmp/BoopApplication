package com.boop.owners.persistence.repository;

import com.boop.owners.persistence.entity.MedicalAction;
import com.boop.owners.persistence.entity.Note;
import com.boop.owners.persistence.entity.PetOwner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findAll();

    Optional<MedicalAction> findNoteByPetOwner(PetOwner petOwner);
}
