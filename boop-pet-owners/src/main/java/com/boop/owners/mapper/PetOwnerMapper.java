package com.boop.owners.mapper;

import com.boop.owners.dto.PetOwnerResponse;
import com.boop.owners.persistence.entity.PetOwner;

public class PetOwnerMapper {

    public static PetOwnerResponse toUserResponse(PetOwner petOwner)  {
        return new PetOwnerResponse(
                petOwner.getId(),
                petOwner.getLogin(),
                petOwner.getPhone(),
                petOwner.getEmail(),
                petOwner.getFirstName(),
                petOwner.getLastName(),
                petOwner.getAbout()
        );
    }
}
