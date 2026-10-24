package com.oldworld.rosterbuilder.army.model;

import com.oldworld.rosterbuilder.army.Faction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateArmyRequest(
        @NotBlank
        @Size(max = 50)
        String name,
        @Size(max = 500)
        String description,
        @NotNull
        @Positive
        Double pointsLimit,
        @NotNull
        Faction faction
) {
}
