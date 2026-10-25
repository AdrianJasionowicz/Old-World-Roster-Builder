package com.oldworld.rosterbuilder.unit;

public enum UnitType {
    LORDS(0.5, false, 100, 0),
    HERO(0.5, false, 50, 0),
    CORE(0.25, true, 0, 0),
    SPECIAL(0.5, false, 0, 3),
    RARE(0.25, false, 0, 2);

    public static final double CHARACTERS_POINTS_SHARE = 0.5;
    public static final double LARGE_GAME_POINTS = 3000;

    private final double armyPointsShare;
    private final boolean minimumShare;
    private final double magicItemsPointsLimit;
    private final int maxDuplicates;

    UnitType(double armyPointsShare, boolean minimumShare, double magicItemsPointsLimit, int maxDuplicates) {
        this.armyPointsShare = armyPointsShare;
        this.minimumShare = minimumShare;
        this.magicItemsPointsLimit = magicItemsPointsLimit;
        this.maxDuplicates = maxDuplicates;
    }

    public double pointsLimit(double armyPointsLimit) {
        return armyPointsLimit * armyPointsShare;
    }

    public boolean isWithinLimit(double usedPoints, double armyPointsLimit) {
        var limit = pointsLimit(armyPointsLimit);
        return minimumShare ? usedPoints >= limit : usedPoints <= limit;
    }

    public boolean isCharacter() {
        return this == LORDS || this == HERO;
    }

    public boolean canChangeSize() {
        return !isCharacter();
    }

    public double getMagicItemsPointsLimit() {
        return magicItemsPointsLimit;
    }

    public boolean hasDuplicateLimit() {
        return maxDuplicates > 0;
    }

    public int duplicateLimit(double armyPointsLimit) {
        return armyPointsLimit >= LARGE_GAME_POINTS ? maxDuplicates * 2 : maxDuplicates;
    }
}
