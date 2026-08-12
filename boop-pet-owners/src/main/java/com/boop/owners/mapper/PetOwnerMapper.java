package com.boop.owners.mapper;

import com.boop.mapper.ResponseMapper;
import com.boop.owners.dto.PetOwnerResponse;
import com.boop.owners.persistence.entity.PetOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PetOwnerMapper implements ResponseMapper<PetOwnerResponse, PetOwner> {

    private final PetMapper petMapper;
    private final NoteMapper noteMapper;

    public PetOwnerResponse toResponse(PetOwner petOwner)  {
        return new PetOwnerResponse(
                petOwner.getId(),
                petOwner.getLogin(),
                petOwner.getPhone(),
                petOwner.getEmail(),
                petOwner.getFirstName(),
                petOwner.getLastName(),
                petOwner.getAbout(),
                petMapper.toResponses(petOwner.getPets()),
                noteMapper.toResponses(petOwner.getNotes())
        );
    }
}
