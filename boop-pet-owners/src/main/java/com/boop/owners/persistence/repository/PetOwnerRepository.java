package com.boop.owners.persistence.repository;

import com.boop.owners.persistence.entity.PetOwner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PetOwnerRepository extends CrudRepository<PetOwner, Long> {

    List<PetOwner> findAll();

    Optional<PetOwner> findPetOwnerByLogin(String login);
}
