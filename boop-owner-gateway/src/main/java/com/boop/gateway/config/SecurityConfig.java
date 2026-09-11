package com.boop.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    public SecurityConfig(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    private ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http)  {
        return http.csrf(csrf -> csrf.disable())
                .authorizeExchange (exchange -> exchange
                        .pathMatchers(
                                "/actuator/**",
                                "/error/**",
                                "/openapi/**",
                                "/webjars/**",
                                "/api/admin/**",
                                "/bff-owner-actuator/**",
                                "/bff-owner/openapi/**",
                                "/bff-owner/webjars/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/bff/pet-owners").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(login -> login.authenticationSuccessHandler(customSuccessHandler()))
                .logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler()))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public ServerAuthenticationSuccessHandler customSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(
                        this.clientRegistrationRepository);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("/actuator");
        return oidcLogoutSuccessHandler;
    }
}
