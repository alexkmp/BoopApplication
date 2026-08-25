package com.boop.service.marketplace.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public record ServiceClaimRequest(
        ServiceClaimType serviceClaimType,
        BigInteger petId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal priceMin,
        BigDecimal priceMax
) {
}
