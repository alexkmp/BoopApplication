package com.boop.specialists.dto;

import java.math.BigDecimal;

public record SpecialistServiceResponse(
        String title,
        BigDecimal price
) {
}
