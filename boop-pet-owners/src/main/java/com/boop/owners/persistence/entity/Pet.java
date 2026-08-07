package com.boop.owners.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pet")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Pet extends BaseEntity {

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_owner_id", nullable = false, insertable = false, updatable = false)
    PetOwner petOwner;

    @NonNull
    @Column(name = "name")
    String name;

    @NonNull
    @Column(name = "pet_type")
    @Enumerated(EnumType.STRING)
    com.boop.owners.dto.PetType petType;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id", referencedColumnName = "id")
    PetBreed breed;

    @Column(name = "character_traits")
    Set<String> characterTraits;

    @OneToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY,
            targetEntity = MedicalAction.class,
            mappedBy = "pet")
    List<MedicalAction> medicalActions;

    @OneToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY,
            targetEntity = MedicalExamination.class,
            mappedBy = "pet")
    List<MedicalExamination> medicalExaminations;
}
