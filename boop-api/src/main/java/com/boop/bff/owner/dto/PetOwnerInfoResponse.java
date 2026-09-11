package com.boop.bff.owner.dto;

import java.util.List;

public record PetOwnerInfoResponse(
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
