package com.boop.owners.dto;

//todo расставить notnull
public record PetOwnerRequest(
        String login,
        String phone,
        String email,
        String password,
        String firstName,
        String lastName,
        String about
) {}