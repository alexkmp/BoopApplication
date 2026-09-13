package com.boop.bff.specialist.integration;

import com.boop.admin.dto.KeycloakUserInfo;
import com.boop.exception.BoopException;
import com.boop.exception.BoopNotFoundException;
import com.boop.keycloak.KeycloakApiService;
import com.boop.specialists.dto.PetSpecialistDataFullResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static java.util.logging.Level.FINE;

@Component
@RequiredArgsConstructor
public class PetSpecialistServiceIntegration {

    private final WebClient specialistClient;
    private final KeycloakApiService keycloakApiService;

    private Logger log = LoggerFactory.getLogger(PetSpecialistServiceIntegration.class);

    public Mono<PetSpecialistDataFullResponse> getPetSpecialist(String login, String token) {
        return specialistClient.get().uri(
                        uriBuilder -> uriBuilder
                                .path("/api/pet-specialists/find")
                                .queryParam("login", login)
                                .build()
                )
                .headers((header) -> header.setBearerAuth(token))
                .retrieve().bodyToMono(PetSpecialistDataFullResponse.class)
                .log(log.getName(), FINE)
                .onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
    }

    public Mono<KeycloakUserInfo> getPetSpecialistKeycloakData(String username) {
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
