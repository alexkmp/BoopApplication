package com.boop.service.marketplace.mapper;

import com.boop.service.marketplace.dto.ServiceClaimResponse;
import com.boop.service.marketplace.persistence.entity.ServiceClaim;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceClaimMapper implements ResponseMapper<ServiceClaimResponse, ServiceClaim> {

    private final SpecialistBidMapper specialistBidMapper;
    private final ServiceReviewMapper serviceReviewMapper;

    @Override
    public ServiceClaimResponse toResponse(ServiceClaim serviceClaim) {
        return new ServiceClaimResponse(
                serviceClaim.getId(),
                serviceClaim.getServiceClaimType(),
                serviceClaim.getServiceClaimStatus(),
                serviceClaim.getPetId(),
                serviceClaim.getStartDate(),
                serviceClaim.getEndDate(),
                serviceClaim.getPriceMin(),
                serviceClaim.getPriceMax(),
                specialistBidMapper.toResponses(serviceClaim.getSpecialistBids()),
                serviceReviewMapper.toResponses(serviceClaim.getServiceReviews())
        );
    }
}
