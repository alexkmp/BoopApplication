package com.boop.admin.dto;

import java.util.List;

public record KeycloakUserInfo(
        String id,
        String username,
        String firstname,
        String lastName,
        String email,
        Boolean emailVerified,
        List<KeycloakUserRole> roles
) {
}
