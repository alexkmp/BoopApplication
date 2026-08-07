package com.boop.owners.persistence.repository;

import com.boop.owners.persistence.entity.MedicalAction;
import com.boop.owners.dto.MedicalActionType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalActionRepository extends CrudRepository<MedicalAction, Long> {

    List<MedicalAction> findAll();

    Optional<MedicalAction> findMedicalActionByMedicalActionType(MedicalActionType medicalActionType);
}
