package com.armybuilderv2.armyBuilderV2.unit.model;

import com.armybuilderv2.armyBuilderV2.unit.UnitFaction;
import com.armybuilderv2.armyBuilderV2.unit.UnitType;

public record UnitRequest(String name,
                          double pointsCostPerUnit,
                          double minQuantity,
                          UnitType unitType,
                          UnitFaction unitFaction) {
}
