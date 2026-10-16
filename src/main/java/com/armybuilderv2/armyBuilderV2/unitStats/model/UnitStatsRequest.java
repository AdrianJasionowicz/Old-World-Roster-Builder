package com.armybuilderv2.armyBuilderV2.unitStats.model;

import jakarta.validation.constraints.PositiveOrZero;

public record UnitStatsRequest(
        @PositiveOrZero
        Integer m,
        @PositiveOrZero
        Integer ws,
        @PositiveOrZero
        Integer bs,
        @PositiveOrZero
        Integer s,
        @PositiveOrZero
        Integer t,
        @PositiveOrZero
        Integer w,
        @PositiveOrZero
        Integer i,
        @PositiveOrZero
        Integer a,
        @PositiveOrZero
        Integer ld,
        @PositiveOrZero
        Integer basicSave,
        @PositiveOrZero
        Integer wardSave
) {
}