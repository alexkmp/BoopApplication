package com.boop.bff.owner.controller;

import com.boop.bff.owner.api.BffPetOwnerOperations;
import com.boop.bff.owner.dto.PetOwnerInfoResponse;
import com.boop.bff.owner.service.PetOwnerService;
import com.boop.exception.BoopNotFoundException;
import com.boop.owners.dto.PetOwnerDataFullResponse;
import com.boop.owners.dto.PetOwnerRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BffPetOwnerOperationController implements BffPetOwnerOperations {

    private final PetOwnerService petOwnerService;

    private Logger log = LoggerFactory.getLogger(BffPetOwnerOperationController.class);

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerInfoResponse> getLoggedOwnerInfo(Authentication auth) throws BoopNotFoundException {
        return petOwnerService.getLoggedOwnerInfo(auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerDataFullResponse> create(PetOwnerRequest petOwnerRequest) {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<PetOwnerDataFullResponse> update(Long id, PetOwnerRequest petOwnerRequest, Authentication auth) throws BoopNotFoundException {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER')")
    public Mono<Boolean> delete(Long id, Authentication auth) {
        return null;
    }
}
