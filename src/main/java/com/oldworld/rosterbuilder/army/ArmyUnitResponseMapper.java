package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.armyUnit.model.ArmyUnitResponse;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedUpgrade;
import com.oldworld.rosterbuilder.specialRule.SpecialRuleView;
import com.oldworld.rosterbuilder.unitStats.StatsCalculatorService;
import com.oldworld.rosterbuilder.upgrade.model.ProfileView;
import com.oldworld.rosterbuilder.upgrade.model.UpgradeView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ArmyUnitResponseMapper {

    private final StatsCalculatorService statsCalculatorService;

    ArmyUnitResponse makeView(ArmyUnit armyUnit) {
        var unit = armyUnit.getUnit();
        var stats = statsCalculatorService.calculate(armyUnit);
        var upgrades = armyUnit.getSelectedUpgradesList().stream()
                .map(ArmyUnitResponseMapper::toUpgradeView)
                .toList();
        return new ArmyUnitResponse(
                armyUnit.getId(),
                unit.getName(),
                armyUnit.getQuantity(),
                armyUnit.getTotalCost(),
                stats.m(),
                stats.ws(),
                stats.bs(),
                stats.s(),
                stats.t(),
                stats.w(),
                stats.i(),
                stats.a(),
                stats.ld(),
                stats.basicSave(),
                stats.wardSave(),
                upgrades,
                unit.getUnitType(),
                armyUnit.isDetachment() ? armyUnit.getParent().getId() : null,
                unit.isCanHaveDetachments() && !armyUnit.isDetachment(),
                unit.magicItemsAllowance(),
                statsCalculatorService.calculateChampion(armyUnit).orElse(null),
                armyUnit.getProfiles().stream().map(ProfileView::from).toList(),
                unit.canChangeSize(),
                unit.getSpecialRulesList().stream().map(SpecialRuleView::from).toList());
    }

    private static UpgradeView toUpgradeView(SelectedUpgrade selectedUpgrade) {
        var upgrade = selectedUpgrade.getUpgrade();
        return new UpgradeView(selectedUpgrade.getId(), upgrade.getName(), selectedUpgrade.getPointsCost(), upgrade.getUpgradeType(), upgrade.getDescription());
    }
}
