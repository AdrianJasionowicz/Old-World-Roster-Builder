package com.oldworld.rosterbuilder.exception.model;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message,
        String path,
        List<FieldErrorResponse> errors
) {
}
