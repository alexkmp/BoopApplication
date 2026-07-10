package com.boop.specialists.config;

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
                                "/specialists-actuator/**",
                                "/error/**",
                                "/specialists/openapi/**",
                                "/specialists/webjars/**"
                        ).permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/pet-specialists").permitAll()
                        .anyExchange().authenticated()
                )
            .oauth2ResourceServer (oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
            .build();
    }
}
