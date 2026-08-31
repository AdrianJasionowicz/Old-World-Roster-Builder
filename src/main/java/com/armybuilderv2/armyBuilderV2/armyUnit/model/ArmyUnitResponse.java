package com.armybuilderv2.armyBuilderV2.armyUnit.model;

import com.armybuilderv2.armyBuilderV2.unit.UnitType;
import com.armybuilderv2.armyBuilderV2.upgrade.model.UpgradeView;

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
        UnitType unitType
) {
        }

