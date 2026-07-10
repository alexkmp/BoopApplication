package com.boop.specialists.persistence;

import com.boop.exception.BoopNotFoundException;
import com.boop.specialists.dto.PetSpecialistRequest;
import com.boop.specialists.dto.PetSpecialistResponse;
import com.boop.specialists.mapper.PetSpecialistMapper;
import com.boop.specialists.persistence.entity.PetSpecialist;
import com.boop.specialists.persistence.repository.PetSpecialistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.boop.specialists.mapper.PetSpecialistMapper.toSpecialistResponse;

@Service
public class PetSpecialistService {

    private PetSpecialistRepository petSpecialistRepository;

    private Logger log = LoggerFactory.getLogger(PetSpecialistService.class);

    public PetSpecialistService(PetSpecialistRepository petSpecialistRepository) {
        this.petSpecialistRepository = petSpecialistRepository;
    }

    public List<PetSpecialistResponse> getAllPetSpecialists() {
        log.info("Get all pet specialists");
        return petSpecialistRepository.findAll().stream().map(PetSpecialistMapper::toSpecialistResponse).collect(Collectors.toUnmodifiableList());
    }

    public PetSpecialistResponse getById(Long id) throws BoopNotFoundException {
        log.info("Get pet specialist by id: {}", id);
        Optional<PetSpecialist> specialist = petSpecialistRepository.findById(id);
        if (!specialist.isPresent()) throw new BoopNotFoundException("Специалист с id:" + id + "не найден");
        return toSpecialistResponse(specialist.get());
    }

    public PetSpecialistResponse create(PetSpecialistRequest petSpecialistRequest) {
        log.info("Create pet specialist, request: {}", petSpecialistRequest);
        PetSpecialist specialist = new PetSpecialist(
                petSpecialistRequest.login(),
                petSpecialistRequest.phone(),
                petSpecialistRequest.email(),
                petSpecialistRequest.firstName(),
                petSpecialistRequest.lastName()
        );
        return toSpecialistResponse(petSpecialistRepository.save(specialist));
    }

    public PetSpecialistResponse update(Long id, PetSpecialistRequest petOwnerRequest) throws BoopNotFoundException {
        log.info("Update pet specialist with id: {}, request: {}", id, petOwnerRequest);
        Optional<PetSpecialist> specialist = petSpecialistRepository.findById(id);
        specialist.ifPresentOrElse(s -> {
            s.setEmail(petOwnerRequest.email());
            s.setPhone(petOwnerRequest.phone());
        },() -> new BoopNotFoundException("Владелец с id:" + id + "не найден"));

        return toSpecialistResponse(petSpecialistRepository.save(specialist.get()));
    }

    public Boolean delete(Long id) {
        log.info("Delete pet owner with id: {}", id);
        petSpecialistRepository.deleteById(id);
        return true;
    }
}
