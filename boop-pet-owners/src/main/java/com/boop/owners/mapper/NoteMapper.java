package com.boop.owners.mapper;

import com.boop.owners.dto.NoteResponse;
import com.boop.owners.persistence.entity.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper implements ResponseMapper<NoteResponse, Note> {

    @Override
    public NoteResponse toResponse(Note note) {
        return new NoteResponse(
                note.getTitle(),
                note.getText()
        );
    }
}
