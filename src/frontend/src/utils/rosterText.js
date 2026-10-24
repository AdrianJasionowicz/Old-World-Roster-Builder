import { FACTIONS } from "../constants/factions";
import { UNIT_TYPES } from "../constants/unitTypes";
import { FREE_UPGRADE } from "../constants/upgradeTypes";
import { categoryPoints, formatPoints } from "./armyPoints";
import { withDetachments } from "./detachments";

export function rosterAsText(army, summary) {
    const faction = FACTIONS.find(f => f.value === army.faction)?.label ?? army.faction;
    const lines = [`${army.name} - ${faction} - ${formatPoints(summary.usedPoints)} / ${formatPoints(army.pointsLimit)} pts`];

    for (const type of UNIT_TYPES) {
        const units = army.armyUnitResponseList.filter(unit => unit.unitType === type.value);
        if (units.length === 0) {
            continue;
        }
        const points = categoryPoints(summary, type.value);
        const limit = `${points.isMinimum ? "min " : ""}${formatPoints(points.limit)}`;
        lines.push("", `== ${type.label} (${formatPoints(points.used)} / ${limit}) ==`);

        for (const unit of withDetachments(units)) {
            const options = unit.selectedUpgrades
                .filter(upgrade => upgrade.upgradeType !== FREE_UPGRADE)
                .map(upgrade => upgrade.name);
            const optionText = options.length > 0 ? `: ${options.join(", ")}` : "";
            const prefix = unit.parentId != null ? "  - detachment: " : "";
            lines.push(`${prefix}${unit.quantity} ${unit.unitName} [${formatPoints(unit.totalCost)} pts]${optionText}`);
        }
    }

    if (summary.violations.length > 0) {
        lines.push("", "Rule problems:", ...summary.violations.map(violation => `- ${violation}`));
    }
    return lines.join("\n");
}
