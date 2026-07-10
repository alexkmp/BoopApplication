package com.boop.specialists.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

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
    String login;

    @NonNull
    String phone;

    @NonNull
    String email;

    @NonNull
    String firstName;

    @NonNull
    String lastName;

    String about;
}
