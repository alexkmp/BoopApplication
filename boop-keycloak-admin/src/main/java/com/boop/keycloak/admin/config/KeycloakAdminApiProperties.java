package com.boop.keycloak.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeycloakAdminApiProperties(
        String realm,
        String clientUuid,
        String endpoint,
        String adminClientId,
        String adminClientSecret
) {
}
