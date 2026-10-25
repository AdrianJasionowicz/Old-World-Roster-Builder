package com.oldworld.rosterbuilder.army.model;

import com.oldworld.rosterbuilder.army.Faction;
import com.oldworld.rosterbuilder.armyUnit.model.ArmyUnitResponse;
import com.oldworld.rosterbuilder.specialRule.SpecialRuleView;

import java.util.List;

public record ArmyView(
        Long id,
        String name,
        String description,
        Double pointsLimit,
        Faction faction,
        List<ArmyUnitResponse> armyUnitResponseList,
        List<SpecialRuleView> armyRules
) {
}
