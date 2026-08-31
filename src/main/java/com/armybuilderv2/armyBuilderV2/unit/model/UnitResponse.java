package com.armybuilderv2.armyBuilderV2.unit.model;

import com.armybuilderv2.armyBuilderV2.unit.UnitType;

public record UnitResponse(Long id,
                           String name, double pointsCostPerUnit, double totalCost, UnitType unitType) {
}
