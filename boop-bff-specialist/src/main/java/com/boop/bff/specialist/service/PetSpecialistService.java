package com.boop.bff.specialist.service;

import com.boop.admin.dto.KeycloakUserInfo;
import com.boop.admin.dto.KeycloakUserRole;
import com.boop.bff.specialist.integration.PetSpecialistServiceIntegration;
import com.boop.bff.specialist.dto.PetSpecialistInfoResponse;
import com.boop.jwt.JwtUtils;
import com.boop.specialists.dto.PetSpecialistDataFullResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static java.util.logging.Level.FINE;

@Service
@RequiredArgsConstructor
public class PetSpecialistService {

    private Logger log = LoggerFactory.getLogger(PetSpecialistService.class);

    private final PetSpecialistServiceIntegration petSpecialistServiceIntegration;
    private final JwtUtils jwtUtils;

    public Mono<PetSpecialistInfoResponse> getLoggedSpecialistInfo(Authentication auth) {
        String login = jwtUtils.getPrincipalNameFromAuth(auth);
        String token = jwtUtils.getToken(auth);
        return Mono.zip(
                values -> createPetSpecialistInfoResponse(
                        (PetSpecialistDataFullResponse) values[0], (KeycloakUserInfo) values[1]),
                        petSpecialistServiceIntegration.getPetSpecialist(login, token),
                        petSpecialistServiceIntegration.getPetSpecialistKeycloakData(login)
                ).doOnError(ex -> log.warn("getPetOwner failed: {}", ex.toString()))
                .log(log.getName(), FINE);
    }

    private PetSpecialistInfoResponse createPetSpecialistInfoResponse(PetSpecialistDataFullResponse specialistDataResponse, KeycloakUserInfo ownerKeycloakData) {
        return new PetSpecialistInfoResponse(
                specialistDataResponse.id(), specialistDataResponse.login(), specialistDataResponse.phone(), specialistDataResponse.email(),
                specialistDataResponse.firstName(), specialistDataResponse.lastName(), specialistDataResponse.about(),
                ownerKeycloakData.roles().stream().map(KeycloakUserRole::name).collect(Collectors.toUnmodifiableList())
        );
    }
}
