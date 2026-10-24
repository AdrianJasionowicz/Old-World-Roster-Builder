package com.oldworld.rosterbuilder.selectedUpgrade;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.exception.BusinessRuleException;
import org.springframework.stereotype.Service;

@Service
public class SelectedQuantityValidator {

    public void validateArmyUnitQuantity(ArmyUnit armyUnit) {
        checkUnitSize(armyUnit);
        recalculateQuantityAndCost(armyUnit);
    }

    public void recalculateQuantityAndCost(ArmyUnit armyUnit) {
        synchronizeQuantity(armyUnit);
        calculateArmyUnitCost(armyUnit);
    }

    void calculateArmyUnitCost(ArmyUnit armyUnit) {
        var upgradesCost = armyUnit.getSelectedUpgradesList().stream()
                .mapToDouble(SelectedUpgrade::getTotalCost)
                .sum();
        armyUnit.setTotalCost(armyUnit.getQuantity() * armyUnit.getUnit().getPointsCostPerUnit() + upgradesCost);
    }

    void synchronizeQuantity(ArmyUnit armyUnit) {
        armyUnit.getSelectedUpgradesList()
                .forEach(selectedUpgrade -> selectedUpgrade.syncQuantityWith(armyUnit.getQuantity()));
    }

    void checkUnitSize(ArmyUnit armyUnit) {
        if (armyUnit.getQuantity() < armyUnit.getMinQuantity()) {
            throw new BusinessRuleException("Unit cannot be decreased");
        }
    }

    public void checkIfUnitSizeCanBeChanged(ArmyUnit armyUnit) {
        var unitType = armyUnit.getUnit().getUnitType();
        if (!unitType.canChangeSize()) {
            throw new BusinessRuleException("Unit size cannot be changed for " + unitType + " units");
        }
    }
}
