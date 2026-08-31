package com.armybuilderv2.armyBuilderV2.unit.model;

import com.armybuilderv2.armyBuilderV2.specialRule.SpecialRuleRequest;
import com.armybuilderv2.armyBuilderV2.unit.UnitFaction;
import com.armybuilderv2.armyBuilderV2.unit.UnitType;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitStatsRequest;
import com.armybuilderv2.armyBuilderV2.upgrade.model.UpgradeRequest;

import java.util.List;

public record UnitRequest(
        String name,
        double pointsCostPerUnit,
        Integer minQuantity,
        UnitType unitType,
        UnitFaction unitFaction,
        List<SpecialRuleRequest> specialRuleList,
        UnitStatsRequest unitStatsRequest,
        List<UpgradeRequest> upgradesList
) {
}





