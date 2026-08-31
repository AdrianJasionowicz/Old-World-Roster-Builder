package com.armybuilderv2.armyBuilderV2.upgrade.model;

import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;

public record UpgradeRequest(
        String name,
        double pointsCost,
        UpgradeType upgradeType,
        String description
) {
}
