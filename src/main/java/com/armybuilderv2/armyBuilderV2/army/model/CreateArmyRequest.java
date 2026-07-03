package com.armybuilderv2.armyBuilderV2.army.model;

import com.armybuilderv2.armyBuilderV2.army.Faction;
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
