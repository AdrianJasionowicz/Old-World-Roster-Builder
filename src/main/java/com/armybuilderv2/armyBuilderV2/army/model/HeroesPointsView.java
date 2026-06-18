package com.armybuilderv2.armyBuilderV2.army.model;

public record HeroesPointsView(
        double usedHeroes,
        double availableHeroes,
        boolean valid
) {
}
