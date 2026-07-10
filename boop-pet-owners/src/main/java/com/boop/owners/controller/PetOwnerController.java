package com.boop.owners.controller;

import com.boop.exception.BoopNotFoundException;
import com.boop.owners.api.PetOwnerOperations;
import com.boop.owners.dto.PetOwnerRequest;
import com.boop.owners.dto.PetOwnerResponse;
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
    public Mono<List<PetOwnerResponse>> getAll() {
        return Mono.just(petOwnerService.getAllPetOwners());
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerResponse> getById(Long id) throws BoopNotFoundException {
        return Mono.just(petOwnerService.getById(id));
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerResponse> findByPhoneOrEmail(String phone, String email) {
        return Mono.just(null);
    }

    @Override
    public Mono<PetOwnerResponse> create(PetOwnerRequest petOwnerRequest) {
        return Mono.just(petOwnerService.create(petOwnerRequest));
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerResponse> update(Long id, PetOwnerRequest petOwnerRequest) throws BoopNotFoundException {
        return Mono.just(petOwnerService.update(id, petOwnerRequest));
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<Boolean> delete(Long id) {
        return Mono.just(petOwnerService.delete(id));
    }
}
