package com.boop.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ResponseMapper <R, E> {

    R toResponse(E entity);

    default List<R> toResponses(List<E> entities) {
        return Optional.ofNullable(entities)
                .map(List::stream).orElseGet(Stream::empty)
                .map(this::toResponse).collect(Collectors.toList());
    }
}
