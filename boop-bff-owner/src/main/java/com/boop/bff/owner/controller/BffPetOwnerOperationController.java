package com.boop.bff.owner.controller;

import com.boop.bff.owner.api.BffPetOwnerOperations;
import com.boop.bff.owner.dto.PetOwnerInfoResponse;
import com.boop.bff.owner.service.PetOwnerService;
import com.boop.exception.BoopNotFoundException;
import com.boop.owners.dto.PetOwnerDataFullResponse;
import com.boop.owners.dto.PetOwnerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class BffPetOwnerOperationController implements BffPetOwnerOperations {

    private final PetOwnerService petOwnerService;

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerInfoResponse> getLoggedOwnerInfo(Principal principal) throws BoopNotFoundException {
        return petOwnerService.getPetOwner(principal.getName());
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerDataFullResponse> create(PetOwnerRequest petOwnerRequest) {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerDataFullResponse> update(Long id, PetOwnerRequest petOwnerRequest) throws BoopNotFoundException {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<Boolean> delete(Long id) {
        return null;
    }
}
