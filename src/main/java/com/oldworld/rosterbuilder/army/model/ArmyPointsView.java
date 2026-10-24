package com.oldworld.rosterbuilder.army.model;

import java.util.List;

public record ArmyPointsView(
        double usedPoints,
        double pointsLimit,
        LordsPointsView lordsPointsView,
        HeroesPointsView heroesPointsView,
        CorePointsView corePointsView,
        SpecialPointsView specialPointsView,
        RarePointsView rarePointsView,
        List<String> violations,
        boolean valid
) {
}
