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

    public PetSpecialistResponse getById(Long id) throws BoopNotFoundException {
        log.info("Get pet specialist by id: {}", id);
        Optional<PetSpecialist> specialist = petSpecialistRepository.findById(id);
        if (!specialist.isPresent()) throw new BoopNotFoundException("Специалист с id:" + id + "не найден");
        return petSpecialistMapper.toResponse(specialist.get());
    }

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

    public PetSpecialistResponse update(Long id, PetSpecialistRequest petOwnerRequest) throws BoopNotFoundException {
        log.info("Update pet specialist with id: {}, request: {}", id, petOwnerRequest);
        Optional<PetSpecialist> specialist = petSpecialistRepository.findById(id);
        specialist.ifPresentOrElse(s -> {
            s.setEmail(petOwnerRequest.email());
            s.setPhone(petOwnerRequest.phone());
        },() -> new BoopNotFoundException("Владелец с id:" + id + "не найден"));

        return petSpecialistMapper.toResponse(petSpecialistRepository.save(specialist.get()));
    }

    public Boolean delete(Long id) {
        log.info("Delete pet owner with id: {}", id);
        petSpecialistRepository.deleteById(id);
        return true;
    }
}
