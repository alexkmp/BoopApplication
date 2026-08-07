package com.boop.owners.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface ResponseMapper <R, E> {

    R toResponse(E entity);

    default List<R> toResponses(List<E> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toUnmodifiableList());
    }
}
