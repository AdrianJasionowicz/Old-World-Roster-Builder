package com.oldworld.rosterbuilder.army.model;

public record HeroesPointsView(
        double usedHeroes,
        double availableHeroes,
        boolean valid
) {
}
