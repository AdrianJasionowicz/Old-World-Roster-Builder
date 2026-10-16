package com.armybuilderv2.armyBuilderV2.unit.model;

import com.armybuilderv2.armyBuilderV2.specialRule.SpecialRuleRequest;
import com.armybuilderv2.armyBuilderV2.unit.UnitFaction;
import com.armybuilderv2.armyBuilderV2.unit.UnitType;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitStatsRequest;
import com.armybuilderv2.armyBuilderV2.upgrade.model.UpgradeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record UnitRequest(
        @NotBlank
        String name,
        @Positive
        double pointsCostPerUnit,
        @NotNull
        @Positive
        Integer minQuantity,
        @NotNull
        UnitType unitType,
        @NotNull
        UnitFaction unitFaction,
        List<SpecialRuleRequest> specialRuleList,
        @NotNull
        UnitStatsRequest unitStatsRequest,
        @NotNull
        List<@Valid UpgradeRequest> upgradesList
) {
}