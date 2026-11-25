package com.boop.specialists.persistence;

import com.boop.exception.BoopNotFoundException;
import com.boop.specialists.dto.PetSpecialistDataFullResponse;
import com.boop.specialists.dto.PetSpecialistRequest;
import com.boop.specialists.mapper.PetSpecialistMapper;
import com.boop.specialists.persistence.entity.PetSpecialist;
import com.boop.specialists.persistence.repository.PetSpecialistRepository;
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
public class PetSpecialistService {

    private final PetSpecialistRepository petSpecialistRepository;
    private final PetSpecialistMapper petSpecialistMapper;

    private Logger log = LoggerFactory.getLogger(PetSpecialistService.class);

    public List<PetSpecialistDataFullResponse> getAllPetSpecialists() {
        log.debug("Get all pet specialists");
        return petSpecialistMapper.toResponses(petSpecialistRepository.findAll());
    }

    @Cacheable(value = "specialist.byId", key = "#id")
    public PetSpecialistDataFullResponse getById(Long id) throws BoopNotFoundException {
        log.debug("Get pet specialist by id: {}", id);
        PetSpecialist specialist = petSpecialistRepository.findById(id).orElseThrow(
                () -> new BoopNotFoundException("Specialist with id:" + id + "not found")
        );
        return petSpecialistMapper.toResponse(specialist);
    }

    @Cacheable(value = "specialist.byLogin", key = "#login")
    public PetSpecialistDataFullResponse findByLogin(String login) throws BoopNotFoundException {
        log.debug("Get pet owner by login: {}", login);
        PetSpecialist owner = petSpecialistRepository.findPetSpecialistByLogin(login).orElseThrow(
                () -> new BoopNotFoundException("Specialist with login:" + login + "not found")
        );
        return petSpecialistMapper.toResponse(owner);
    }

    @CachePut(value = "specialist.byId", key = "#result.id()")
    public PetSpecialistDataFullResponse create(PetSpecialistRequest petSpecialistRequest) {
        log.debug("Create pet specialist, request: {}", petSpecialistRequest);
        PetSpecialist specialist = new PetSpecialist(
                petSpecialistRequest.login(),
                petSpecialistRequest.phone(),
                petSpecialistRequest.email(),
                petSpecialistRequest.firstName(),
                petSpecialistRequest.lastName(),
                petSpecialistRequest.about()
        );
        return petSpecialistMapper.toResponse(petSpecialistRepository.save(specialist));
    }

    @CachePut(value = "specialist,byId", key = "#result.id()")
    public PetSpecialistDataFullResponse update(Long id, PetSpecialistRequest petSpecialistRequest) throws BoopNotFoundException {
        log.debug("Update pet specialist with id: {}, request: {}", id, petSpecialistRequest);
        PetSpecialist specialist = petSpecialistRepository.findById(id).orElseThrow(
                () -> new BoopNotFoundException("Specialist with id:" + id + "not found")
        );
        specialist.setEmail(petSpecialistRequest.email());
        specialist.setPhone(petSpecialistRequest.phone());

        return petSpecialistMapper.toResponse(petSpecialistRepository.save(specialist));
    }

    @CacheEvict(value = "specialist.byId", key = "#id")
    public Boolean delete(Long id) {
        log.debug("Delete pet specialist with id: {}", id);
        petSpecialistRepository.deleteById(id);
        return true;
    }
}
