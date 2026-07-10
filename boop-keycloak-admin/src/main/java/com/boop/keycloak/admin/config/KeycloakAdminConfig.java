package com.boop.keycloak.admin.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {

    private KeycloakAdminApiProperties keycloakAdminApiProperties;

    public KeycloakAdminConfig(KeycloakAdminApiProperties keycloakAdminApiProperties) {
        this.keycloakAdminApiProperties = keycloakAdminApiProperties;
    }

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .serverUrl(keycloakAdminApiProperties.endpoint())
                .realm(keycloakAdminApiProperties.realm())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(keycloakAdminApiProperties.adminClientId())
                .clientSecret(keycloakAdminApiProperties.adminClientSecret())
                .build();
    }
}
