package com.boop.owners.persistence.repository;

import com.boop.owners.persistence.entity.PetBreed;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PetBreedRepository extends CrudRepository<PetBreed, Long> {

    List<PetBreed> findAll();

    Optional<PetBreed> findPetBreedByName(String name);
}
