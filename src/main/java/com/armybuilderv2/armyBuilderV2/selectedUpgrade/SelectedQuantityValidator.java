package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitCannotBeDecreasedException;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitSizeCannotBeChangedException;
import com.armybuilderv2.armyBuilderV2.exception.UpgradeNotFoundException;
import com.armybuilderv2.armyBuilderV2.unit.UnitType;
import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectedQuantityValidator {

    public void validateArmyUnitQuantity(ArmyUnit armyUnit) {
        checkUnitSize(armyUnit);
        synchronizeQuantity(armyUnit);
        calculateArmyUnitCost(armyUnit);
    }

    public void recalculateQuantityAndCost(ArmyUnit armyUnit) {
        synchronizeQuantity(armyUnit);
        calculateArmyUnitCost(armyUnit);
    }


    void calculateArmyUnitCost(ArmyUnit armyUnit) {
        double totalCost = 0.0;
        totalCost += armyUnit.getQuantity()*armyUnit.getUnit().getPointsCostPerUnit();

        List<SelectedUpgrade> selectedUpgradeList = armyUnit.getSelectedUpgradesList();
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
            totalCost += selectedUpgrade.getQuantity()*selectedUpgrade.getUpgrade().getPointsCost();
        }
        armyUnit.setTotalCost(totalCost);
    }
    void synchronizeQuantity(ArmyUnit armyUnit) {
        List<SelectedUpgrade> selectedUpgradeList = armyUnit.getSelectedUpgradesList();
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
            if (selectedUpgrade.getUpgrade().getUpgradeType().equals(UpgradeType.UNIT_EQUIPMENT)) {
                selectedUpgrade.setQuantity(armyUnit.getQuantity());
            }
        }
    }
    void checkUnitSize(ArmyUnit armyUnit) {
        if (armyUnit.getQuantity() < armyUnit.getUnit().getMinQuantity()) {
            throw new ArmyUnitCannotBeDecreasedException("Unit cannot be decreased");
        }
    }

    public void checkIfUnitSizeCanBeChanged(ArmyUnit armyUnit) {
        UnitType unitType = armyUnit.getUnit().getUnitType();

        if (unitType == UnitType.HERO
                || unitType == UnitType.LORDS
                || unitType == UnitType.RARE) {
            throw new ArmyUnitSizeCannotBeChangedException(
                    "Unit size cannot be changed for " + unitType + " units"
            );
        }
    }

    public void checkUpgradeQuantities(ArmyUnit armyUnit, Long upgradeId) {
        SelectedUpgrade selectedUpgrade = armyUnit.getSelectedUpgradesList().stream()
                .filter(su -> su.getUpgrade().getId().equals(upgradeId))
                .findFirst()
                .orElseThrow(() -> new UpgradeNotFoundException(armyUnit.getUnit().getUnitType().toString()));
        switch (selectedUpgrade.getUpgrade().getUpgradeType()) {
            case UNIT_EQUIPMENT:
                selectedUpgrade.setQuantity(armyUnit.getQuantity());
                break;
            default:
                selectedUpgrade.setQuantity(1);
        }
    }
}
