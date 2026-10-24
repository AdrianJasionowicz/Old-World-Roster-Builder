package com.oldworld.rosterbuilder.army.model;

public record LordsPointsView(
        double usedLords,
        double availableLords,
        boolean valid
) {
}
