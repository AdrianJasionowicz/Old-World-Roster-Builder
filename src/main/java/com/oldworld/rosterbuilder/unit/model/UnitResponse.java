package com.oldworld.rosterbuilder.unit.model;

import com.oldworld.rosterbuilder.unit.UnitType;

public record UnitResponse(
        Long id,
        String name,
        double pointsCostPerUnit,
        double totalCost,
        UnitType unitType,
        boolean canBeDetachment
) {
}
