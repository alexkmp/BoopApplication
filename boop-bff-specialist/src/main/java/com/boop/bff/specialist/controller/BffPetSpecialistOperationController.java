package com.boop.bff.specialist.controller;

import com.boop.bff.specialist.service.PetSpecialistService;
import com.boop.bff.specialist.api.BffPetSpecialistOperations;
import com.boop.bff.specialist.dto.PetSpecialistInfoResponse;
import com.boop.exception.BoopNotFoundException;
import com.boop.specialists.dto.PetSpecialistDataFullResponse;
import com.boop.specialists.dto.PetSpecialistRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BffPetSpecialistOperationController implements BffPetSpecialistOperations {

    private final PetSpecialistService petSpecialistService;

    private Logger log = LoggerFactory.getLogger(BffPetSpecialistOperationController.class);

    @Override
    @PreAuthorize("hasAnyRole('SPECIALIST')")
    public Mono<PetSpecialistInfoResponse> getLoggedSpecialistInfo(Authentication auth) throws BoopNotFoundException {
        return petSpecialistService.getLoggedSpecialistInfo(auth);
    }

    @Override
    @PreAuthorize("hasAnyRole('SPECIALIST')")
    public Mono<PetSpecialistDataFullResponse> create(PetSpecialistRequest petSpecialistRequest) {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('SPECIALIST')")
    public Mono<PetSpecialistDataFullResponse> update(Long id, PetSpecialistRequest petSpecialistRequest, Authentication auth) throws BoopNotFoundException {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('SPECIALIST')")
    public Mono<Boolean> delete(Long id, Authentication auth) {
        return null;
    }
}
