package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.exception.UpgradeNotFoundException;
import com.armybuilderv2.armyBuilderV2.unit.Unit;
import com.armybuilderv2.armyBuilderV2.unit.UnitType;
import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SelectedQuantityValidatorTest {


    @InjectMocks
    SelectedQuantityValidator selectedQuantityValidator;

    @Test
    @DisplayName("checkUpgradeQuantities happy path")
    void checkUpgradeQuantities() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(1L);

        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);

        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(UpgradeType.WEAPON_TEAM);
        upgrade.setPointsCost(51);

        selectedUpgrade.setUpgrade(upgrade);
        selectedUpgrade.setQuantity(5);

        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        selectedUpgradeList.add(selectedUpgrade);

        armyUnit.setSelectedUpgradesList(selectedUpgradeList);

        selectedQuantityValidator.checkUpgradeQuantities(armyUnit, 2L);

        assertEquals(1, selectedUpgrade.getQuantity());
    }


    @Test
    @DisplayName("checkUpgradeQuantities exception: UpgradeNotFoundException")
    void checkUpgradeQuantitiesWithUpgradeNotFoundException() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(1L);
        Unit unit = new Unit();
        unit.setUnitType(UnitType.CORE);
        armyUnit.setUnit(unit);
        armyUnit.setSelectedUpgradesList(new ArrayList<>());

        assertThrows(
                UpgradeNotFoundException.class,
                () -> selectedQuantityValidator.checkUpgradeQuantities(armyUnit, 999L)
        );
    }
}