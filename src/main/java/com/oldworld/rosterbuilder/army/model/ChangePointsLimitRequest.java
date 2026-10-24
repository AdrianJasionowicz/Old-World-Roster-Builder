package com.oldworld.rosterbuilder.army.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChangePointsLimitRequest(
        @NotNull
        @Positive
        Double pointsLimit
) {
}
