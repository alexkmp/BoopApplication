package com.boop.specialists.dto;

//todo расставить notnull
public record PetSpecialistRequest(
        String login,
        String phone,
        String email,
        String password,
        String firstName,
        String lastName,
        String about
) {}