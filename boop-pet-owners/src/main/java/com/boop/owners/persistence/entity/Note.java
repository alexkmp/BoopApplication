package com.boop.owners.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "note")
@Access(AccessType.FIELD)
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Note extends BaseEntity {

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_owner_id", nullable = false, insertable = false, updatable = false)
    PetOwner petOwner;

    @NonNull
    @Column(name = "title")
    String title;

    @NonNull
    @Column(name = "note_text")
    String text;
}
