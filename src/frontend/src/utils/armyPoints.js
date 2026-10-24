export function categoryPoints(summary, unitType) {
    if (!summary) {
        return null;
    }
    switch (unitType) {
        case "LORDS":
            return limit(summary.lordsPointsView.usedLords, summary.lordsPointsView.availableLords, summary.lordsPointsView.valid);
        case "HERO":
            return limit(summary.heroesPointsView.usedHeroes, summary.heroesPointsView.availableHeroes, summary.heroesPointsView.valid);
        case "CORE":
            return limit(summary.corePointsView.usedCore, summary.corePointsView.minimalCore, summary.corePointsView.valid, true);
        case "SPECIAL":
            return limit(summary.specialPointsView.usedSpecial, summary.specialPointsView.availableSpecial, summary.specialPointsView.valid);
        case "RARE":
            return limit(summary.rarePointsView.usedRare, summary.rarePointsView.availableRare, summary.rarePointsView.valid);
        default:
            return null;
    }
}

function limit(used, max, valid, isMinimum = false) {
    return { used, limit: max, valid, isMinimum };
}

export function formatPoints(points) {
    return Number.isInteger(points) ? String(points) : points.toFixed(1);
}
