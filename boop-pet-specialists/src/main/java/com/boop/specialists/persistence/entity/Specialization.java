package com.boop.specialists.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "specialization")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_specialist_id", nullable = false, insertable = false, updatable = false)
    PetSpecialist petSpecialist;

    @NonNull
    @Column(name = "title")
    String title;

    @NonNull
    @Column(name = "factor")
    Integer factor;
}
