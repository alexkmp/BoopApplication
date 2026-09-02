package com.boop.specialists.persistence;

import com.boop.exception.BoopNotFoundException;
import com.boop.specialists.dto.PetSpecialistRequest;
import com.boop.specialists.dto.PetSpecialistResponse;
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
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetSpecialistService {

    private final PetSpecialistRepository petSpecialistRepository;
    private final PetSpecialistMapper petSpecialistMapper;

    private Logger log = LoggerFactory.getLogger(PetSpecialistService.class);

    public List<PetSpecialistResponse> getAllPetSpecialists() {
        log.info("Get all pet specialists");
        return petSpecialistMapper.toResponses(petSpecialistRepository.findAll());
    }

    @Cacheable(value = "specialist", key = "#id")
    public PetSpecialistResponse getById(Long id) throws BoopNotFoundException {
        log.info("Get pet specialist by id: {}", id);
        Optional<PetSpecialist> specialist = petSpecialistRepository.findById(id);
        if (!specialist.isPresent()) throw new BoopNotFoundException("Specialist with id:" + id + "not found");
        return petSpecialistMapper.toResponse(specialist.get());
    }

    @CachePut(value = "specialist", key = "#result.id()")
    public PetSpecialistResponse create(PetSpecialistRequest petSpecialistRequest) {
        log.info("Create pet specialist, request: {}", petSpecialistRequest);
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

    @CachePut(value = "specialist", key = "#result.id()")
    public PetSpecialistResponse update(Long id, PetSpecialistRequest petSpecialistRequest) throws BoopNotFoundException {
        log.info("Update pet specialist with id: {}, request: {}", id, petSpecialistRequest);
        Optional<PetSpecialist> specialist = petSpecialistRepository.findById(id);
        specialist.ifPresentOrElse(s -> {
            s.setEmail(petSpecialistRequest.email());
            s.setPhone(petSpecialistRequest.phone());
        },() -> new BoopNotFoundException("Specialist with id:" + id + "not found"));

        return petSpecialistMapper.toResponse(petSpecialistRepository.save(specialist.get()));
    }

    @CacheEvict(value = "specialist", key = "#id")
    public Boolean delete(Long id) {
        log.info("Delete pet specialist with id: {}", id);
        petSpecialistRepository.deleteById(id);
        return true;
    }
}
