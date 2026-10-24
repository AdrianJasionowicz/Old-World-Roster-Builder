package com.oldworld.rosterbuilder.upgrade.model;

import com.oldworld.rosterbuilder.upgrade.UpgradeType;
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