package com.boop.specialists.mapper;

import com.boop.mapper.ResponseMapper;
import com.boop.specialists.dto.PetSpecialistResponse;
import com.boop.specialists.persistence.entity.PetSpecialist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetSpecialistMapper implements ResponseMapper<PetSpecialistResponse, PetSpecialist> {

    private final SpecializationMapper specializationMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final SpecialistServiceMapper specialistServiceMapper;

    @Override
    public PetSpecialistResponse toResponse(PetSpecialist petSpecialist) {
        return new PetSpecialistResponse(
                petSpecialist.getId(),
                petSpecialist.getLogin(),
                petSpecialist.getPhone(),
                petSpecialist.getEmail(),
                petSpecialist.getFirstName(),
                petSpecialist.getLastName(),
                petSpecialist.getAbout(),
                specializationMapper.toOrderedResponses(petSpecialist.getSpecializations()),
                workExperienceMapper.toResponses(petSpecialist.getWorkExperiences()),
                specialistServiceMapper.toResponses(petSpecialist.getSpecialistServices())
        );
    }
}
