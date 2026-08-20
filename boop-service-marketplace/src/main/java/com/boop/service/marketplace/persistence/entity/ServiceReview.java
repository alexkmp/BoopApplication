package com.boop.service.marketplace.persistence.entity;

import com.boop.service.marketplace.dto.ReviewType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table(name = "service_review")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class ServiceReview extends BaseEntity {

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_claim_id", nullable = false, insertable = false, updatable = false)
    ServiceClaim serviceClaim;

    @Column(name = "reviewer_id")
    BigInteger reviewerId;

    @Column(name = "review_type")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    ReviewType reviewType;

    @Column(name = "description")
    String description;

    @Column(name = "rating")
    Integer rating;
}
