package com.boop.owners.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_action")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class MedicalAction extends BaseEntity {

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false, insertable = false, updatable = false)
    Pet pet;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    com.boop.owners.dto.MedicalActionType medicalActionType;

    @NonNull
    @Column(name = "description")
    String description;

    @NonNull
    @Column(name = "action_date")
    LocalDateTime actionDate;

    @NonNull
    @Column(name = "expiration_date")
    LocalDateTime expirationDate;
}
