package com.boop.specialists.controller;

import com.boop.exception.BoopNotFoundException;
import com.boop.specialists.api.PetSpecialistOperations;
import com.boop.specialists.dto.PetSpecialistRequest;
import com.boop.specialists.dto.PetSpecialistResponse;
import com.boop.specialists.persistence.PetSpecialistService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class PetSpecialistController implements PetSpecialistOperations {

    private PetSpecialistService petSpecialistService;

    public PetSpecialistController(PetSpecialistService petSpecialistService) {
        this.petSpecialistService = petSpecialistService;
    }

    @Override
    @PreAuthorize("hasAnyRole('SPECIALIST')")
    public Mono<List<PetSpecialistResponse>> getAll() {
        return Mono.just(petSpecialistService.getAllPetSpecialists());
    }

    @Override
    @PreAuthorize("hasAnyRole('SPECIALIST')")
    public Mono<PetSpecialistResponse> getById(Long id) throws BoopNotFoundException {
        return Mono.just(petSpecialistService.getById(id));
    }

    @Override
    @PreAuthorize("hasAnyRole('SPECIALIST')")
    public Mono<PetSpecialistResponse> findByPhoneOrEmail(String phone, String email) {
        return Mono.just(null);
    }

    @Override
    public Mono<PetSpecialistResponse> create(PetSpecialistRequest petSpecialistRequest) {
        return Mono.just(petSpecialistService.create(petSpecialistRequest));
    }

    @Override
    @PreAuthorize("hasAnyRole('SPECIALIST')")
    public Mono<PetSpecialistResponse> update(Long id, PetSpecialistRequest petSpecialistRequest) throws BoopNotFoundException {
        return Mono.just(petSpecialistService.update(id, petSpecialistRequest));
    }

    @Override
    @PreAuthorize("hasAnyRole('SPECIALIST')")
    public Mono<Boolean> delete(Long id) {
        return Mono.just(petSpecialistService.delete(id));
    }
}
