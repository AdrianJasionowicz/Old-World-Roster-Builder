package com.oldworld.rosterbuilder.army.model;

import com.oldworld.rosterbuilder.army.Faction;

public record CreateArmyResponse(
        Long id,
        String name,
        String description,
        Double pointsLimit,
        Faction faction
) {
}
