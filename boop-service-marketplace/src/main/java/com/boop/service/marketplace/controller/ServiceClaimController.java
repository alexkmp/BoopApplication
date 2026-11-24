package com.boop.service.marketplace.controller;

import com.boop.exception.BoopNotFoundException;
import com.boop.service.marketplace.api.ServiceClaimOperations;
import com.boop.service.marketplace.dto.ServiceClaimRequest;
import com.boop.service.marketplace.dto.ServiceClaimResponse;
import com.boop.service.marketplace.persistence.ServiceClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServiceClaimController implements ServiceClaimOperations {

    private final ServiceClaimService serviceClaimService;

    @Override
    public Mono<List<ServiceClaimResponse>> getAll() {
        return Mono.just(serviceClaimService.getAllServiceClaims());
    }

    @Override
    public Mono<ServiceClaimResponse> getById(Long id) throws BoopNotFoundException {
        return Mono.just(serviceClaimService.getServiceClaimById(id));
    }

    @Override
    public Mono<ServiceClaimResponse> create(ServiceClaimRequest serviceClaimRequest) {
        return Mono.just(serviceClaimService.createServiceClaim(serviceClaimRequest));
    }

    @Override
    public Mono<ServiceClaimResponse> update(Long id, ServiceClaimRequest serviceClaimRequest) throws BoopNotFoundException {
        return Mono.just(serviceClaimService.update(id, serviceClaimRequest));
    }

    @Override
    public Mono<Boolean> delete(Long id) {
        return Mono.just(serviceClaimService.delete(id));
    }
}
