package com.oldworld.rosterbuilder.selectedUpgrade.model;

import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import com.oldworld.rosterbuilder.upgrade.RuneKind;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;

public record UpgradeViewCombined(
        Long id,
        String name,
        double totalCost,
        UpgradeType upgradeType,
        String description,
        RuneKind rune,
        boolean selected,
        boolean allowed,
        String notAllowedReason
) {

    public static UpgradeViewCombined from(UnitUpgrade unitUpgrade, boolean selected, String notAllowedReason) {
        var upgrade = unitUpgrade.getUpgrade();
        return new UpgradeViewCombined(unitUpgrade.getId(), upgrade.getName(), unitUpgrade.getPointsCost(), upgrade.getUpgradeType(),
                upgrade.getDescription(), upgrade.getRune(), selected, notAllowedReason == null, notAllowedReason);
    }
}
