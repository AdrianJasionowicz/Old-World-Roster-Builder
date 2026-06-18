package com.armybuilderv2.armyBuilderV2.selectedUpgrade.model;

import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;

public record UpgradeViewCombined(
        Long id,
        String name,
        double totalCost,
        UpgradeType upgradeType,
        String description,
        boolean selected

) {
}
