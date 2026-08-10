package com.boop.owners.persistence;

import com.boop.exception.BoopNotFoundException;
import com.boop.owners.dto.PetOwnerRequest;
import com.boop.owners.dto.PetOwnerResponse;
import com.boop.owners.mapper.PetOwnerMapper;
import com.boop.owners.persistence.entity.PetOwner;
import com.boop.owners.persistence.repository.PetOwnerRepository;
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
public class PetOwnerService {

    private final PetOwnerMapper petOwnerMapper;
    private final PetOwnerRepository petOwnerRepository;

    private Logger log = LoggerFactory.getLogger(PetOwnerService.class);

    public List<PetOwnerResponse> getAllPetOwners() {
        log.info("Get all pet owners");
        return petOwnerMapper.toResponses(petOwnerRepository.findAll());
    }

    public PetOwnerResponse getById(Long id) throws BoopNotFoundException {
        log.info("Get pet owner by id: {}", id);
        Optional<PetOwner> owner = petOwnerRepository.findById(id);
        if (!owner.isPresent()) throw new BoopNotFoundException("Владелец с id:" + id + "не найден");
        return petOwnerMapper.toResponse(owner.get());
    }

    public PetOwnerResponse create(PetOwnerRequest petOwnerRequest) {
        log.info("Create pet owner, request: {}", petOwnerRequest);
        PetOwner owner = new PetOwner(
                petOwnerRequest.login(),
                petOwnerRequest.phone(),
                petOwnerRequest.email(),
                petOwnerRequest.firstName(),
                petOwnerRequest.lastName(),
                petOwnerRequest.about()
        );
        return petOwnerMapper.toResponse(petOwnerRepository.save(owner));
    }

    public PetOwnerResponse update(Long id, PetOwnerRequest petOwnerRequest) throws BoopNotFoundException {
        log.info("Update pet owner with id: {}, request: {}", id, petOwnerRequest);
        Optional<PetOwner> owner = petOwnerRepository.findById(id);
        owner.ifPresentOrElse(o -> {
            o.setEmail(petOwnerRequest.email());
            o.setPhone(petOwnerRequest.phone());
        },() -> new BoopNotFoundException("Владелец с id:" + id + "не найден"));

        return petOwnerMapper.toResponse(petOwnerRepository.save(owner.get()));
    }

    public Boolean delete(Long id) {
        log.info("Delete pet owner with id: {}", id);
        petOwnerRepository.deleteById(id);
        return true;
    }
}
