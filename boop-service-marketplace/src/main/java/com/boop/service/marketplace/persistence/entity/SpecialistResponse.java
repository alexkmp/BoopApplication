package com.boop.service.marketplace.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table(name = "specialist_response")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class SpecialistResponse extends BaseEntity {

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_request_id", nullable = false, insertable = false, updatable = false)
    ServiceRequest serviceRequest;

    @Column(name = "specialist_id")
    BigInteger specialistId;

    @Column(name = "message")
    @Getter @Setter
    String message;
}
