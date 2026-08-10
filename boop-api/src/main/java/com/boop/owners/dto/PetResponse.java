package com.boop.owners.dto;

import java.util.List;
import java.util.Set;

public record PetResponse(
        String name,
        PetType petType,
        String petBreed,
        Set<String> characterTraits,
        List<MedicalActionResponse> medicalActions,
        List<MedicalExaminationResponse> medicalExaminations
) {
}
