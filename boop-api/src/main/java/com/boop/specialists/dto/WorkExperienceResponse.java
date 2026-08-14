package com.boop.specialists.dto;

public record WorkExperienceResponse(
        String companyName,
        String jobTitle,
        Integer jobDuration
) {
}
