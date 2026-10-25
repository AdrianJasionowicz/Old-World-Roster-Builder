package com.oldworld.rosterbuilder.unit.model;

import com.oldworld.rosterbuilder.specialRule.SpecialRuleRequest;
import com.oldworld.rosterbuilder.unit.UnitFaction;
import com.oldworld.rosterbuilder.unit.UnitType;
import com.oldworld.rosterbuilder.unitStats.model.UnitStatsRequest;
import com.oldworld.rosterbuilder.upgrade.model.UpgradeRequest;
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
        List<@Valid SpecialRuleRequest> specialRuleList,
        @NotNull
        @Valid
        UnitStatsRequest unitStatsRequest,
        @NotNull
        List<@Valid UpgradeRequest> upgradesList,
        boolean fixedSize
) {
}