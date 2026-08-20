package com.boop.service.marketplace.persistence;

import com.boop.exception.BoopNotFoundException;
import com.boop.service.marketplace.dto.ServiceClaimRequest;
import com.boop.service.marketplace.dto.ServiceClaimResponse;
import com.boop.service.marketplace.dto.ServiceClaimStatus;
import com.boop.service.marketplace.mapper.ServiceClaimMapper;
import com.boop.service.marketplace.persistence.entity.ServiceClaim;
import com.boop.service.marketplace.persistence.repository.ServiceClaimRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger log = LoggerFactory.getLogger(ServiceClaimService.class);

    public List<ServiceClaimResponse> getAllServiceClaims() {
        log.info("get all service claims");
        return serviceClaimMapper.toResponses(serviceClaimRepository.findAll());
    }

    public ServiceClaimResponse getServiceClaimById(Long id) {
        log.info("get service claim by id: {}", id);
        Optional<ServiceClaim> serviceClaim = serviceClaimRepository.findById(id);
        if (!serviceClaim.isPresent()) throw new BoopNotFoundException("Service claim with id:" + id + "not found");
        return serviceClaimMapper.toResponse(serviceClaim.get());
    }

    public ServiceClaimResponse createServiceClaim(ServiceClaimRequest serviceClaimRequest) {
        log.info("create service claim, request: {}", serviceClaimRequest);
        ServiceClaim serviceClaim = new ServiceClaim(
                serviceClaimRequest.serviceClaimType(),
                ServiceClaimStatus.DRAFT,
                serviceClaimRequest.startDate(),
                serviceClaimRequest.endDate(),
                serviceClaimRequest.priceMin(),
                serviceClaimRequest.priceMax()
        );
        return serviceClaimMapper.toResponse(serviceClaimRepository.save(serviceClaim));
    }

    public ServiceClaimResponse update(Long id, ServiceClaimRequest serviceClaimRequest) {
        log.info("update service claim with id: {}, request: {}", id, serviceClaimRequest);
        Optional<ServiceClaim> serviceClaim = serviceClaimRepository.findById(id);
        serviceClaim.ifPresentOrElse(s -> {
            s.setStartDate(serviceClaimRequest.startDate());
            s.setEndDate(serviceClaimRequest.endDate());
            s.setPriceMin(serviceClaimRequest.priceMin());
            s.setPriceMax(serviceClaimRequest.priceMax());
        },() -> new BoopNotFoundException("Service claim with id:" + id + "not found"));

        return serviceClaimMapper.toResponse(serviceClaimRepository.save(serviceClaim.get()));
    }

    public Boolean delete(Long id) {
        log.info("delete service claim with id: {}", id);
        serviceClaimRepository.deleteById(id);
        return true;
    }
}
