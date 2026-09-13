package com.boop.specialists.mapper;

import com.boop.mapper.ResponseMapper;
import com.boop.specialists.dto.PetSpecialistDataFullResponse;
import com.boop.specialists.persistence.entity.PetSpecialist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetSpecialistMapper implements ResponseMapper<PetSpecialistDataFullResponse, PetSpecialist> {

    private final SpecializationMapper specializationMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final SpecialistServiceMapper specialistServiceMapper;

    @Override
    public PetSpecialistDataFullResponse toResponse(PetSpecialist petSpecialist) {
        return new PetSpecialistDataFullResponse(
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
