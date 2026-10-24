package com.oldworld.rosterbuilder.support;

import com.oldworld.rosterbuilder.selectedUpgrade.SelectedUpgrade;
import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import com.oldworld.rosterbuilder.upgrade.Upgrade;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;

public final class UpgradeFixtures {

    private UpgradeFixtures() {
    }

    public static UnitUpgrade unitUpgrade(long id, String name, UpgradeType upgradeType, double pointsCost) {
        Upgrade upgrade = new Upgrade(name, upgradeType, name);
        upgrade.setId(id);
        UnitUpgrade unitUpgrade = new UnitUpgrade(null, upgrade, pointsCost);
        unitUpgrade.setId(id);
        return unitUpgrade;
    }

    public static SelectedUpgrade selected(UnitUpgrade unitUpgrade) {
        return new SelectedUpgrade(null, unitUpgrade);
    }

    public static SelectedUpgrade selected(long unitUpgradeId, UpgradeType upgradeType, double pointsCost) {
        return selected(unitUpgrade(unitUpgradeId, "Upgrade " + unitUpgradeId, upgradeType, pointsCost));
    }

    public static SelectedUpgrade selected(long unitUpgradeId, UpgradeType upgradeType) {
        return selected(unitUpgradeId, upgradeType, 0);
    }
}
