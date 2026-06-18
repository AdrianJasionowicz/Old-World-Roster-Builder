package com.armybuilderv2.armyBuilderV2.army.model;

public record ArmyPointsView(
        double usedPoints,
        double pointsLimit,
        LordsPointsView lordsPointsView,
        HeroesPointsView heroesPointsView,
        CorePointsView corePointsView,
        SpecialPointsView specialPointsView,
        RarePointsView rarePointsView,
        boolean valid

) {
}
