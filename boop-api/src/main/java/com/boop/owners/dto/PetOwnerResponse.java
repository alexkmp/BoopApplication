package com.boop.owners.dto;

import java.util.List;

public record PetOwnerResponse(
        Long id,
        String login,
        String phone,
        String email,
        String firstName,
        String lastName,
        String about,
        List<PetResponse> pets,
        List<NoteResponse> notes
) {}
