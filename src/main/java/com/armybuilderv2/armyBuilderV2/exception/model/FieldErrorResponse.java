package com.armybuilderv2.armyBuilderV2.exception.model;

public record FieldErrorResponse(
        String field,
        String message
) {
}
