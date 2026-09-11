package com.boop.owners.controller;

import com.boop.exception.BoopNotFoundException;
import com.boop.owners.api.PetOwnerOperations;
import com.boop.owners.dto.PetOwnerRequest;
import com.boop.owners.dto.PetOwnerDataFullResponse;
import com.boop.owners.persistence.PetOwnerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class PetOwnerController implements PetOwnerOperations {

    private PetOwnerService petOwnerService;

    public PetOwnerController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<List<PetOwnerDataFullResponse>> getAll() {
        return Mono.just(petOwnerService.getAllPetOwners());
    }

    @Override
    //@PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerDataFullResponse> getById(Long id) throws BoopNotFoundException {
        return Mono.just(petOwnerService.getById(id));
    }

    @Override
    //@PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerDataFullResponse> findByLogin(String login) {
        return Mono.just(petOwnerService.findByLogin(login));
    }

    @Override
    public Mono<PetOwnerDataFullResponse> create(PetOwnerRequest petOwnerRequest) {
        return Mono.just(petOwnerService.create(petOwnerRequest));
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerDataFullResponse> update(Long id, PetOwnerRequest petOwnerRequest) throws BoopNotFoundException {
        return Mono.just(petOwnerService.update(id, petOwnerRequest));
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<Boolean> delete(Long id) {
        return Mono.just(petOwnerService.delete(id));
    }
}
