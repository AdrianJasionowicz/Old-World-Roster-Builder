package com.oldworld.rosterbuilder.upgrade.model;

import com.oldworld.rosterbuilder.upgrade.UpgradeType;

public record UpgradeView(
        Long id,
        String name,
        double pointsCost,
        UpgradeType upgradeType,
        String description
) {
}
