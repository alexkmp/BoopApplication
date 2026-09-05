package com.boop.bff.owner.integration;

import com.boop.admin.dto.KeycloakUserInfo;
import com.boop.exception.BoopException;
import com.boop.exception.BoopNotFoundException;
import com.boop.keycloak.KeycloakApiService;
import com.boop.owners.dto.PetOwnerDataFullResponse;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static java.util.logging.Level.FINE;

@Component
public class PetOwnerServiceIntegration {

    private final WebClient ownerClient;
    private final KeycloakApiService keycloakApiService;

    private Logger log = LoggerFactory.getLogger(PetOwnerServiceIntegration.class);

    public PetOwnerServiceIntegration(WebClient.Builder webClientBuilder, KeycloakApiService keycloakApiService, ObservationRegistry observationRegistry) {
        this.ownerClient = webClientBuilder.baseUrl("http://boop-pet-owners")
                .observationRegistry(observationRegistry)
                .build();
        this.keycloakApiService = keycloakApiService;
    }

    public Mono<PetOwnerDataFullResponse> getPetOwner(String login) {
        return ownerClient.get().uri(
                        uriBuilder -> uriBuilder
                                .path("/api/pet-owners/find")
                                .queryParam("login", login)
                                .build()
                ).retrieve().bodyToMono(PetOwnerDataFullResponse.class)
                .log(log.getName(), FINE)
                .onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
    }

    public Mono<KeycloakUserInfo> getPetOwnerKeycloakData(String username) {
        return Mono.just(keycloakApiService.getUserInfoByUsername(username, true));
    }

    private Throwable handleException(Throwable ex) {

        if (!(ex instanceof WebClientResponseException)) {
            log.warn("Got a unexpected error: {}, will rethrow it", ex.toString());
            return ex;
        }

        WebClientResponseException wcre = (WebClientResponseException)ex;

        switch (HttpStatus.resolve(wcre.getStatusCode().value())) {

            case NOT_FOUND:
                return new BoopNotFoundException(getErrorMessage(wcre));

            case UNPROCESSABLE_ENTITY:
                return new BoopException(getErrorMessage(wcre));

            default:
                log.warn("Got an unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
                log.warn("Error body: {}", wcre.getResponseBodyAsString());
                return ex;
        }
    }

    private String getErrorMessage(WebClientResponseException ex) {
        //            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        return ex.getResponseBodyAsString();
    }
}
