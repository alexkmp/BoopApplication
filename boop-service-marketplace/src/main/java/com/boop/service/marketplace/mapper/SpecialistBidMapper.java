package com.boop.service.marketplace.mapper;

import com.boop.mapper.ResponseMapper;
import com.boop.service.marketplace.dto.SpecialistBidResponse;
import com.boop.service.marketplace.persistence.entity.SpecialistBid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpecialistBidMapper implements ResponseMapper<SpecialistBidResponse, SpecialistBid> {

    @Override
    public SpecialistBidResponse toResponse(SpecialistBid specialistBid) {
        return new SpecialistBidResponse(
                specialistBid.getSpecialistId(),
                specialistBid.getMessage()
        );
    }
}
