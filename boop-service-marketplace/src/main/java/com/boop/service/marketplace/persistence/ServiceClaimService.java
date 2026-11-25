package com.boop.service.marketplace.persistence;

import com.boop.event.OutboxAggregateType;
import com.boop.event.OutboxEventType;
import com.boop.event.OutboxStatus;
import com.boop.exception.BoopNotFoundException;
import com.boop.service.marketplace.dto.ServiceClaimRequest;
import com.boop.service.marketplace.dto.ServiceClaimResponse;
import com.boop.service.marketplace.dto.ServiceClaimStatus;
import com.boop.service.marketplace.mapper.ServiceClaimMapper;
import com.boop.service.marketplace.persistence.entity.OutboxEvent;
import com.boop.service.marketplace.persistence.entity.ServiceClaim;
import com.boop.service.marketplace.persistence.repository.OutboxEventRepository;
import com.boop.service.marketplace.persistence.repository.ServiceClaimRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceClaimService {

    private final ServiceClaimMapper serviceClaimMapper;
    private final ServiceClaimRepository serviceClaimRepository;
    private final OutboxEventRepository outboxEventRepository;

    @Qualifier("jackson2ObjectMapper")
    private final ObjectMapper objectMapper;

    private Logger log = LoggerFactory.getLogger(ServiceClaimService.class);

    public List<ServiceClaimResponse> getAllServiceClaims() {
        log.debug("get all service claims");
        return serviceClaimMapper.toResponses(serviceClaimRepository.findAll());
    }

    @Cacheable(value = "service-claim", key = "#id")
    public ServiceClaimResponse getServiceClaimById(Long id) {
        log.debug("get service claim by id: {}", id);
        ServiceClaim serviceClaim = serviceClaimRepository.findById(id).orElseThrow(
                () -> new BoopNotFoundException("Service claim with id:" + id + "not found")
        );
        return serviceClaimMapper.toResponse(serviceClaim);
    }

    @CachePut(value = "service-claim", key = "#result.id()")
    public ServiceClaimResponse createServiceClaim(ServiceClaimRequest serviceClaimRequest) {
        log.debug("create service claim, request: {}", serviceClaimRequest);
        ServiceClaim serviceClaim = ServiceClaim.builder()
                .serviceClaimType(serviceClaimRequest.serviceClaimType())
                .serviceClaimStatus(ServiceClaimStatus.DRAFT)
                .petId(serviceClaimRequest.petId())
                .startDate(serviceClaimRequest.startDate())
                .endDate(serviceClaimRequest.endDate())
                .priceMin(serviceClaimRequest.priceMin())
                .priceMax(serviceClaimRequest.priceMax())
                .build();
        ServiceClaim savedServiceClaim = serviceClaimRepository.save(serviceClaim);
        OutboxEvent outboxEvent = OutboxEvent.builder()
                .aggregateId(savedServiceClaim.getId())
                .aggregateType(OutboxAggregateType.SERVICE_CLAIM)
                .status(OutboxStatus.PENDING)
                .type(OutboxEventType.SERVICE_CLAIM_CREATED)
                .payload(objectMapper.valueToTree(serviceClaimMapper.toServiceClaimCreated(savedServiceClaim)))
                .attempts(0)
                .build();
        outboxEventRepository.save(outboxEvent);
        return serviceClaimMapper.toResponse(savedServiceClaim);
    }

    @CachePut(value = "service-claim", key = "#result.id()")
    public ServiceClaimResponse update(Long id, ServiceClaimRequest serviceClaimRequest) {
        log.debug("update service claim with id: {}, request: {}", id, serviceClaimRequest);
        ServiceClaim serviceClaim = serviceClaimRepository.findById(id).orElseThrow(
                () -> new BoopNotFoundException("Service claim with id:" + id + "not found")
        );
        serviceClaim.setStartDate(serviceClaimRequest.startDate());
        serviceClaim.setEndDate(serviceClaimRequest.endDate());
        serviceClaim.setPriceMin(serviceClaimRequest.priceMin());
        serviceClaim.setPriceMax(serviceClaimRequest.priceMax());

        OutboxEvent outboxEvent = OutboxEvent.builder()
                .aggregateId(serviceClaim.getId())
                .aggregateType(OutboxAggregateType.SERVICE_CLAIM)
                .status(OutboxStatus.PENDING)
                .type(OutboxEventType.SERVICE_CLAIM_UPDATED)
                .payload(objectMapper.valueToTree(serviceClaimMapper.toServiceClaimUpdated(serviceClaim)))
                .attempts(0)
                .build();
        outboxEventRepository.save(outboxEvent);
        return serviceClaimMapper.toResponse(serviceClaim);
    }

    @CacheEvict(value = "service-claim", key = "#id")
    public Boolean delete(Long id) {
        log.debug("delete service claim with id: {}", id);
        serviceClaimRepository.deleteById(id);
        OutboxEvent outboxEvent = OutboxEvent.builder()
                .aggregateId(id)
                .aggregateType(OutboxAggregateType.SERVICE_CLAIM)
                .status(OutboxStatus.PENDING)
                .type(OutboxEventType.SERVICE_CLAIM_DELETED)
                .payload(objectMapper.valueToTree(serviceClaimMapper.toServiceClaimDeleted(id)))
                .attempts(0)
                .build();
        outboxEventRepository.save(outboxEvent);
        return true;
    }
}
