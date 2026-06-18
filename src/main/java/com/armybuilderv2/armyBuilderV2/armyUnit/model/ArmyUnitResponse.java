package com.armybuilderv2.armyBuilderV2.armyUnit.model;

import com.armybuilderv2.armyBuilderV2.upgrade.model.UpgradeView;

import java.util.List;

public record ArmyUnitResponse(
        Long id,
        String unitName,
        Integer quantity,
        Double totalCost,
        List<UpgradeView> selectedUpgrades ){
        }

