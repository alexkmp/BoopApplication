package com.boop.owners.dto;

import java.time.LocalDateTime;

public record MedicalActionResponse(
        MedicalActionType medicalActionType,
        String description,
        LocalDateTime actionDate,
        LocalDateTime expirationDate
) {
}
