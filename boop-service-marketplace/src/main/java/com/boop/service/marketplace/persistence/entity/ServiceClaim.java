package com.boop.service.marketplace.persistence.entity;

import com.boop.service.marketplace.dto.ServiceClaimStatus;
import com.boop.service.marketplace.dto.ServiceClaimType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "service_claim")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class ServiceClaim extends BaseEntity {

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "claim_type")
    ServiceClaimType serviceClaimType;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    ServiceClaimStatus serviceClaimStatus;

    @NonNull
    @Column(name = "pet_id")
    BigInteger petId;

    @NonNull
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    LocalDateTime startDate;

    @NonNull
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
            targetEntity = SpecialistBid.class,
            mappedBy = "serviceClaim")
    List<SpecialistBid> specialistBids;

    @OneToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY,
            targetEntity = ServiceReview.class,
            mappedBy = "serviceClaim")
    List<ServiceReview> serviceReviews;
}
