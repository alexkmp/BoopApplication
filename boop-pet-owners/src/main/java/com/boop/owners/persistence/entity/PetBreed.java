package com.boop.owners.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pet_breed")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class PetBreed extends BaseEntity {

    @NonNull
    @Column(name = "name")
    String name;
}
