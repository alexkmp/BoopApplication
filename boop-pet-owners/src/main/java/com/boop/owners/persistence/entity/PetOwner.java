package com.boop.owners.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "pet_owner")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class PetOwner extends BaseEntity {

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

    @Column(name = "about")
    String about;

    @OneToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY,
            targetEntity = Pet.class,
            mappedBy = "petOwner")
    List<Pet> pets;

    @OneToMany(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY,
            targetEntity = Note.class,
            mappedBy = "petOwner")
    List<Note> notes;
}
