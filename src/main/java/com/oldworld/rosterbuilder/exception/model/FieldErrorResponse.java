package com.oldworld.rosterbuilder.exception.model;

public record FieldErrorResponse(
        String field,
        String message
) {
}
