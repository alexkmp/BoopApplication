package com.boop.specialists.persistence.repository;

import com.boop.specialists.persistence.entity.PetSpecialist;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PetSpecialistRepository extends CrudRepository<PetSpecialist, Long> {

    List<PetSpecialist> findAll();

    Optional<PetSpecialist> findPetOwnersByPhone(String phone);

    Optional<PetSpecialist> findPetOwnerByEmail(String email);
}
