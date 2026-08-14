package com.boop.specialists.mapper;

import com.boop.mapper.ResponseMapper;
import com.boop.specialists.dto.SpecializationResponse;
import com.boop.specialists.persistence.entity.Specialization;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpecializationMapper implements ResponseMapper<SpecializationResponse, Specialization> {

    @Override
    public SpecializationResponse toResponse(Specialization specialization) {
        return new SpecializationResponse(
                specialization.getTitle(),
                specialization.getFactor()
        );
    }

    public List<SpecializationResponse> toOrderedResponses(List<Specialization> specializations) {
        return specializations == null ? Collections.emptyList() : specializations.stream()
                .sorted(Comparator.comparing(Specialization::getFactor).reversed())
                .map(this::toResponse)
                .collect(Collectors.toUnmodifiableList());
    }
}
