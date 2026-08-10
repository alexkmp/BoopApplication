package com.boop.owners.mapper;

import com.boop.owners.dto.PetResponse;
import com.boop.owners.persistence.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetMapper implements ResponseMapper<PetResponse, Pet> {

    private final MedicalActionMapper medicalActionMapper;
    private final MedicalExaminationMapper medicalExaminationMapper;

    @Override
    public PetResponse toResponse(Pet pet) {
        return new PetResponse(
                pet.getName(),
                pet.getPetType(),
                pet.getBreed().getName(),
                pet.getCharacterTraits(),
                medicalActionMapper.toResponses(pet.getMedicalActions()),
                medicalExaminationMapper.toResponses(pet.getMedicalExaminations())
        );
    }
}
