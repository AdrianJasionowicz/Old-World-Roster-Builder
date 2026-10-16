package com.armybuilderv2.armyBuilderV2.upgrade.model;

import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpgradeRequest(
        @NotBlank
        String name,
        @PositiveOrZero
        double pointsCost,
        @NotNull
        UpgradeType upgradeType,
        String description
) {
}