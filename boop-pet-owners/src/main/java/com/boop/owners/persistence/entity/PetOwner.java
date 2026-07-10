package com.boop.owners.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pet_owner")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class PetOwner {

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
