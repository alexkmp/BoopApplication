package com.boop.owners.persistence;

import com.boop.exception.BoopNotFoundException;
import com.boop.owners.dto.PetOwnerDataFullResponse;
import com.boop.owners.dto.PetOwnerRequest;
import com.boop.owners.mapper.PetOwnerMapper;
import com.boop.owners.persistence.entity.PetOwner;
import com.boop.owners.persistence.repository.PetOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PetOwnerService {

    private final PetOwnerMapper petOwnerMapper;
    private final PetOwnerRepository petOwnerRepository;

    private Logger log = LoggerFactory.getLogger(PetOwnerService.class);

    public List<PetOwnerDataFullResponse> getAllPetOwners() {
        log.debug("Get all pet owners");
        return petOwnerMapper.toResponses(petOwnerRepository.findAll());
    }

    @Cacheable(value = "owner.byId", key = "#id")
    public PetOwnerDataFullResponse getById(Long id) throws BoopNotFoundException {
        log.debug("Get pet owner by id: {}", id);
        PetOwner owner = petOwnerRepository.findById(id).orElseThrow(
                () -> new BoopNotFoundException("Owner with id:" + id + "not found")
        );
        return petOwnerMapper.toResponse(owner);

    }

    @Cacheable(value = "owner.byLogin", key = "#login")
    public PetOwnerDataFullResponse findByLogin(String login) throws BoopNotFoundException {
        log.debug("Get pet owner by login: {}", login);
        PetOwner owner = petOwnerRepository.findPetOwnerByLogin(login).orElseThrow(
                () -> new BoopNotFoundException("Owner with login:" + login + "not found")
        );
        return petOwnerMapper.toResponse(owner);

    }

    @CachePut(value = "owner.byId", key = "#result.id()")
    public PetOwnerDataFullResponse create(PetOwnerRequest petOwnerRequest) {
        log.debug("Create pet owner, request: {}", petOwnerRequest);
        PetOwner owner = PetOwner.builder()
                .login(petOwnerRequest.login())
                .phone(petOwnerRequest.phone())
                .email(petOwnerRequest.email())
                .firstName(petOwnerRequest.firstName())
                .lastName(petOwnerRequest.lastName())
                .about(petOwnerRequest.about())
                .build();
        return petOwnerMapper.toResponse(petOwnerRepository.save(owner));
    }

    @CachePut(value = "owner.byId", key = "#result.id()")
    public PetOwnerDataFullResponse update(Long id, PetOwnerRequest petOwnerRequest) throws BoopNotFoundException {
        log.debug("Update pet owner with id: {}, request: {}", id, petOwnerRequest);
        PetOwner owner = petOwnerRepository.findById(id).orElseThrow(
                () -> new BoopNotFoundException("Owner with id:" + id + "not found")
        );
        owner.setEmail(petOwnerRequest.email());
        owner.setPhone(petOwnerRequest.phone());

        return petOwnerMapper.toResponse(petOwnerRepository.save(owner));
    }

    @CacheEvict(value = "owner.byId", key = "#id")
    public Boolean delete(Long id) {
        log.debug("Delete pet owner with id: {}", id);
        petOwnerRepository.deleteById(id);
        return true;
    }
}
