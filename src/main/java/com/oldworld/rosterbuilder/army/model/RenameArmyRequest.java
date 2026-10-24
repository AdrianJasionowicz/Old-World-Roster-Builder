package com.oldworld.rosterbuilder.army.model;

import jakarta.validation.constraints.NotBlank;

public record RenameArmyRequest(
        @NotBlank
        String name
) {
}
