package com.boop.bff.specialist.dto;

import java.util.List;

public record PetSpecialistInfoResponse(
        Long id,
        String login,
        String phone,
        String email,
        String firstName,
        String lastName,
        String about,
        List<String> roles
) {
}
