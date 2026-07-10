package com.boop.owners.dto;

public record PetOwnerResponse(
        Long id,
        String login,
        String phone,
        String email,
        String firstName,
        String lastName,
        String about
) {}
