package com.boop.owners.persistence.repository;

import com.boop.owners.persistence.entity.Pet;
import com.boop.owners.persistence.entity.PetOwner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends CrudRepository<Pet, Long> {

    List<Pet> findAll();

    Optional<Pet> findPetByPetOwner(PetOwner petOwner);
}
