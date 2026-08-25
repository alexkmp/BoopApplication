package com.boop.service.marketplace.dto;

import java.math.BigInteger;

public record ServiceReviewResponse(
        BigInteger reviewerId,
        ReviewType reviewType,
        String description,
        Integer rating
) {
}
