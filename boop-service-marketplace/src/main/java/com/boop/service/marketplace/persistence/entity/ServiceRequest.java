package com.boop.service.marketplace.persistence.entity;

import com.boop.service.marketplace.dto.ServiceRequestStatus;
import com.boop.service.marketplace.dto.ServiceRequestType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "service_request")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class ServiceRequest extends BaseEntity {

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    ServiceRequestType serviceRequestType;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    ServiceRequestStatus serviceRequestStatus;

    @Column(name = "pet_id")
    BigInteger petId;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    LocalDateTime startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    LocalDateTime endDate;

    @NonNull
    @Column(name = "price_min")
    BigDecimal priceMin;

    @NonNull
    @Column(name = "price_max")
    BigDecimal priceMax;

    @OneToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY,
            targetEntity = SpecialistResponse.class,
            mappedBy = "serviceRequest")
    List<SpecialistResponse> responses;

    @OneToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY,
            targetEntity = ServiceReview.class,
            mappedBy = "serviceRequest")
    List<ServiceReview> serviceReviews;
}
