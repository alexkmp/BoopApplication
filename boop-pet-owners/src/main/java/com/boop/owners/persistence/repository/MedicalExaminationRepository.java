package com.boop.owners.persistence.repository;

import com.boop.owners.persistence.entity.MedicalExamination;
import com.boop.owners.persistence.entity.Pet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalExaminationRepository extends CrudRepository<MedicalExamination, Long> {

    List<MedicalExamination> findAll();

    Optional<MedicalExamination> findMedicalExaminationByPet(Pet pet);
}
