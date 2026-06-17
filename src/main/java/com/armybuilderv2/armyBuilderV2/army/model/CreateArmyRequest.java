package com.armybuilderv2.armyBuilderV2.army.model;

import com.armybuilderv2.armyBuilderV2.army.Faction;


public record CreateArmyRequest(
        String name,
        String description,
        Double pointsLimit,
        Faction faction
) {

}
