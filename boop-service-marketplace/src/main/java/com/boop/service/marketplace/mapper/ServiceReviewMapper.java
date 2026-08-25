package com.boop.service.marketplace.mapper;

import com.boop.service.marketplace.dto.ServiceReviewResponse;
import com.boop.service.marketplace.persistence.entity.ServiceReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceReviewMapper implements ResponseMapper<ServiceReviewResponse, ServiceReview> {

    @Override
    public ServiceReviewResponse toResponse(ServiceReview serviceReview) {
        return new ServiceReviewResponse(
                serviceReview.getReviewerId(),
                serviceReview.getReviewType(),
                serviceReview.getDescription(),
                serviceReview.getRating()
        );
    }
}
