package com.boop.specialists.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "work_experience")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_specialist_id", nullable = false, insertable = false, updatable = false)
    PetSpecialist petSpecialist;

    @NonNull
    @Column(name = "company_name")
    String companyName;

    @NonNull
    @Column(name = "job_title")
    String jobTitle;

    @NonNull
    @Column(name = "job_duration")
    Integer jobDuration;
}
