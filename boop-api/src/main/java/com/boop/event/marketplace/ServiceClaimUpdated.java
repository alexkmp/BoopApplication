package com.boop.event.marketplace;

import com.boop.service.marketplace.dto.ServiceClaimType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public record ServiceClaimUpdated(
        Long id,
        ServiceClaimType serviceClaimType,
        Long petId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal priceMin,
        BigDecimal priceMax
) {
}
