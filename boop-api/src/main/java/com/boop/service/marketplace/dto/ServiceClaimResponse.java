package com.boop.service.marketplace.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public record ServiceClaimResponse(
        Long id,
        ServiceClaimType serviceClaimType,
        ServiceClaimStatus serviceClaimStatus,
        BigInteger petId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal priceMin,
        BigDecimal priceMax,
        List<SpecialistBidResponse> responses,
        List<ServiceReviewResponse> serviceReviews
        ) {
}
