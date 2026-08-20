package com.boop.service.marketplace.dto;

import java.math.BigInteger;

public record SpecialistBidResponse(
        BigInteger specialistId,
        String message
) {
}
