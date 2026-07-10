package com.boop.specialists.mapper;

import com.boop.specialists.dto.PetSpecialistResponse;
import com.boop.specialists.persistence.entity.PetSpecialist;

public class PetSpecialistMapper {

    public static PetSpecialistResponse toSpecialistResponse(PetSpecialist petSpecialist)  {
        return new PetSpecialistResponse(
                petSpecialist.getId(),
                petSpecialist.getLogin(),
                petSpecialist.getPhone(),
                petSpecialist.getEmail(),
                petSpecialist.getFirstName(),
                petSpecialist.getLastName(),
                petSpecialist.getAbout()
        );
    }
}
