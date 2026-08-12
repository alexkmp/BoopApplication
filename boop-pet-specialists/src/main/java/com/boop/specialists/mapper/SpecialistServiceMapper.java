package com.boop.specialists.mapper;

import com.boop.mapper.ResponseMapper;
import com.boop.specialists.dto.SpecialistServiceResponse;
import com.boop.specialists.persistence.entity.SpecialistService;
import org.springframework.stereotype.Component;

@Component
public class SpecialistServiceMapper implements ResponseMapper<SpecialistServiceResponse, SpecialistService> {

    @Override
    public SpecialistServiceResponse toResponse(SpecialistService specialistService) {
        return new SpecialistServiceResponse(
                specialistService.getTitle(),
                specialistService.getPrice()
        );
    }
}
