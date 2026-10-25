package com.oldworld.rosterbuilder.armyUnit.model;

import com.oldworld.rosterbuilder.specialRule.SpecialRuleView;
import com.oldworld.rosterbuilder.unit.UnitType;
import com.oldworld.rosterbuilder.unitStats.model.ChampionDetails;
import com.oldworld.rosterbuilder.upgrade.model.ProfileView;
import com.oldworld.rosterbuilder.upgrade.model.UpgradeView;

import java.util.List;

public record ArmyUnitResponse(
        Long id,
        String unitName,
        Integer quantity,
        Double totalCost,
        Integer m,
        Integer ws,
        Integer bs,
        Integer s,
        Integer t,
        Integer w,
        Integer i,
        Integer a,
        Integer ld,
        Integer basicSave,
        Integer wardSave,
        List<UpgradeView> selectedUpgrades,
        UnitType unitType,
        Long parentId,
        boolean canHaveDetachments,
        double magicItemsPointsLimit,
        ChampionDetails champion,
        List<ProfileView> profiles,
        boolean resizable,
        List<SpecialRuleView> specialRules
) {
}

