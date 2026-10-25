package com.oldworld.rosterbuilder.selectedUpgrade;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.exception.BusinessRuleException;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unit.UnitType;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.oldworld.rosterbuilder.support.UpgradeFixtures.selected;
import static org.junit.jupiter.api.Assertions.*;

class SelectedQuantityValidatorTest {

    SelectedQuantityValidator selectedQuantityValidator = new SelectedQuantityValidator();

    private ArmyUnit armyUnit(UnitType unitType, int quantity, double pointsCostPerUnit, SelectedUpgrade... selectedUpgrades) {
        Unit unit = new Unit();
        unit.setUnitType(unitType);
        unit.setMinQuantity(10);
        unit.setPointsCostPerUnit(pointsCostPerUnit);
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setUnit(unit);
        armyUnit.setQuantity(quantity);
        armyUnit.setSelectedUpgradesList(new ArrayList<>(List.of(selectedUpgrades)));
        return armyUnit;
    }

    @Test
    @DisplayName("recalculateQuantityAndCost: equipment is bought per model, other upgrades once")
    void recalculateQuantityAndCost() {
        SelectedUpgrade shields = selected(1L, UpgradeType.UNIT_EQUIPMENT, 0.5);
        SelectedUpgrade weaponTeam = selected(2L, UpgradeType.WEAPON_TEAM, 55);
        weaponTeam.setQuantity(5);
        ArmyUnit armyUnit = armyUnit(UnitType.CORE, 20, 4, shields, weaponTeam);

        selectedQuantityValidator.recalculateQuantityAndCost(armyUnit);

        assertEquals(20, shields.getQuantity());
        assertEquals(1, weaponTeam.getQuantity());
        assertEquals(20 * 4 + 20 * 0.5 + 55, armyUnit.getTotalCost());
    }

    @Test
    @DisplayName("validateArmyUnitQuantity exception: BusinessRuleException")
    void validateArmyUnitQuantityBelowMinimum() {
        ArmyUnit armyUnit = armyUnit(UnitType.CORE, 9, 4);
        assertThrows(BusinessRuleException.class, () -> selectedQuantityValidator.validateArmyUnitQuantity(armyUnit));
    }

    @Test
    @DisplayName("checkIfUnitSizeCanBeChanged: core, special and rare units change size, characters do not")
    void checkIfUnitSizeCanBeChanged() {
        assertDoesNotThrow(() -> selectedQuantityValidator.checkIfUnitSizeCanBeChanged(armyUnit(UnitType.CORE, 10, 4)));
        assertDoesNotThrow(() -> selectedQuantityValidator.checkIfUnitSizeCanBeChanged(armyUnit(UnitType.SPECIAL, 10, 4)));
        assertDoesNotThrow(() -> selectedQuantityValidator.checkIfUnitSizeCanBeChanged(armyUnit(UnitType.RARE, 5, 4)));
        assertThrows(BusinessRuleException.class, () -> selectedQuantityValidator.checkIfUnitSizeCanBeChanged(armyUnit(UnitType.HERO, 1, 4)));
    }

    @Test
    @DisplayName("checkIfUnitSizeCanBeChanged: a special war machine keeps its size")
    void checkIfUnitSizeCanBeChangedWarMachine() {
        ArmyUnit greatCannon = armyUnit(UnitType.SPECIAL, 1, 120);
        greatCannon.getUnit().setName("Great Cannon");
        greatCannon.getUnit().setFixedSize(true);

        var exception = assertThrows(BusinessRuleException.class, () -> selectedQuantityValidator.checkIfUnitSizeCanBeChanged(greatCannon));

        assertEquals("Great Cannon has a fixed size", exception.getMessage());
    }

    @Test
    @DisplayName("checkIfUnitSizeCanBeChanged: a single rare monster keeps its size")
    void checkIfUnitSizeCanBeChangedRareMonster() {
        ArmyUnit doomwheel = armyUnit(UnitType.RARE, 1, 150);
        doomwheel.getUnit().setFixedSize(true);

        assertThrows(BusinessRuleException.class, () -> selectedQuantityValidator.checkIfUnitSizeCanBeChanged(doomwheel));
    }

    @Test
    @DisplayName("checkIfUnitSizeCanBeChanged: a single-model special unit that is no war machine can grow")
    void checkIfUnitSizeCanBeChangedSingleModel() {
        assertDoesNotThrow(() -> selectedQuantityValidator.checkIfUnitSizeCanBeChanged(armyUnit(UnitType.SPECIAL, 1, 50)));
    }
}
