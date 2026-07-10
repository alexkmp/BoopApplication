package com.boop.specialists.dto;

public record PetSpecialistResponse(
        Long id,
        String login,
        String phone,
        String email,
        String firstName,
        String lastName,
        String about
) {}
