package com.boop.specialists.dto;

import java.util.List;

public record PetSpecialistResponse(
        Long id,
        String login,
        String phone,
        String email,
        String firstName,
        String lastName,
        String about,
        List<SpecializationResponse> specializations,
        List<WorkExperienceResponse> workExperiences,
        List<SpecialistServiceResponse> specialistServices
) {}
