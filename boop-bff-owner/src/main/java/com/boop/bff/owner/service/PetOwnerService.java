package com.boop.bff.owner.service;

import com.boop.admin.dto.KeycloakUserInfo;
import com.boop.admin.dto.KeycloakUserRole;
import com.boop.bff.owner.dto.PetOwnerInfoResponse;
import com.boop.bff.owner.integration.PetOwnerServiceIntegration;
import com.boop.owners.dto.PetOwnerDataFullResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static java.util.logging.Level.FINE;

@Service
@RequiredArgsConstructor
public class PetOwnerService {

    private Logger log = LoggerFactory.getLogger(PetOwnerService.class);

    private final PetOwnerServiceIntegration petOwnerServiceIntegration;

    public Mono<PetOwnerInfoResponse> getPetOwner(String login) {
        return Mono.zip(
                values -> createPetOwnerInfoResponse((PetOwnerDataFullResponse) values[0], (KeycloakUserInfo) values[1]),
                        petOwnerServiceIntegration.getPetOwner(login),
                        petOwnerServiceIntegration.getPetOwnerKeycloakData(login)
        ).doOnError(ex -> log.warn("getPetOwner failed: {}", ex.toString()))
        .log(log.getName(), FINE);
    }

    private PetOwnerInfoResponse createPetOwnerInfoResponse(PetOwnerDataFullResponse ownerDataResponse, KeycloakUserInfo ownerKeycloakData) {
        return new PetOwnerInfoResponse(
                ownerDataResponse.id(), ownerDataResponse.login(), ownerDataResponse.phone(), ownerDataResponse.email(),
                ownerDataResponse.firstName(), ownerDataResponse.lastName(), ownerDataResponse.about(),
                ownerKeycloakData.roles().stream().map(KeycloakUserRole::name).collect(Collectors.toUnmodifiableList())
        );
    }
}
