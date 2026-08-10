package com.boop.owners.dto;

import java.time.LocalDateTime;

public record MedicalExaminationResponse(
        LocalDateTime examinationDate,
        LocalDateTime expirationDate,
        String report
) {
}
