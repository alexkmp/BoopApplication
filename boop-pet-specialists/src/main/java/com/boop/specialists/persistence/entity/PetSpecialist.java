package com.boop.specialists.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "pet_specialist")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class PetSpecialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    Long id;

    @NonNull
    @Column(name = "login")
    String login;

    @NonNull
    @Column(name = "phone")
    String phone;

    @NonNull
    @Column(name = "email")
    String email;

    @NonNull
    @Column(name = "first_name")
    String firstName;

    @NonNull
    @Column(name = "last_name")
    String lastName;

    @NonNull
    @Column(name = "about")
    String about;

    @OneToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY,
            targetEntity = Specialization.class,
            mappedBy = "petSpecialist")
    List<Specialization> specializations;

    @OneToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY,
            targetEntity = WorkExperience.class,
            mappedBy = "petSpecialist")
    List<WorkExperience> workExperiences;

    @OneToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY,
            targetEntity = SpecialistService.class,
            mappedBy = "petSpecialist")
    List<SpecialistService> specialistServices;
}
