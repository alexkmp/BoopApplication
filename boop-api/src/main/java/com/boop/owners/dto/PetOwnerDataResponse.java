package com.boop.owners.dto;

public record PetOwnerDataResponse(
        Long id,
        String login,
        String phone,
        String email,
        String firstName,
        String lastName,
        String about
) {}
