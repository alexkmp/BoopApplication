package com.boop.owners.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_examination")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class MedicalExamination extends BaseEntity {

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false, insertable = false, updatable = false)
    Pet pet;

    @NonNull
    @Column(name = "examination_date")
    LocalDateTime examinationDate;

    @Column(name = "expiration_date")
    LocalDateTime expirationDate;

    @NonNull
    @Column(name = "report")
    String report;
}
