package com.boop.bff.owner.config;

import com.boop.converter.JwtConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private JwtConverter jwtConverter;

    public SecurityConfig(JwtConverter jwtConverter) {
        this.jwtConverter = jwtConverter;
    }

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http.csrf(csrf -> csrf.disable())
                .authorizeExchange (exchanges ->
                exchanges.pathMatchers(
                                "/headerrouting/**",
                                "/bff-owner-actuator/**",
                                "/error/**",
                                "/bff-owner/openapi/**",
                                "/bff-owner/webjars/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/bff/pet-owners/find").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/bff/pet-owners").permitAll()
                        .anyExchange().authenticated()
                )
            .oauth2ResourceServer (oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
            .build();
    }
}
