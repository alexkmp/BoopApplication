package com.boop.service.marketplace.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table(name = "specialist_bid")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class SpecialistBid extends BaseEntity {

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_claim_id", nullable = false, insertable = false, updatable = false)
    ServiceClaim serviceClaim;

    @Column(name = "specialist_id")
    BigInteger specialistId;

    @Column(name = "message")
    @Getter @Setter
    String message;
}
