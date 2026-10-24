package com.oldworld.rosterbuilder.unitStats;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedUpgrade;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unitStats.model.UnitDetails;
import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.oldworld.rosterbuilder.support.UpgradeFixtures.selected;
import static com.oldworld.rosterbuilder.support.UpgradeFixtures.unitUpgrade;
import static com.oldworld.rosterbuilder.upgrade.ModifierOperation.*;
import static com.oldworld.rosterbuilder.upgrade.StatType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsCalculatorServiceTest {

    @Mock
    CurrentUserService currentUserService;

    StatsCalculatorService statsCalculatorService() {
        return new StatsCalculatorService(currentUserService, new StatsCalculatorValidatorService());
    }

    private ArmyUnit armyUnit(Integer armourSave, Integer wardSave, SelectedUpgrade... selectedUpgrades) {
        UnitStats unitStats = new UnitStats(1L, 7, 4, 4, 4, 4, 2, 5, 2, 8, armourSave, wardSave);
        Unit unit = new Unit();
        unit.setUnitStats(unitStats);
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(4L);
        armyUnit.setUnit(unit);
        armyUnit.setSelectedUpgradesList(new ArrayList<>(List.of(selectedUpgrades)));
        return armyUnit;
    }

    private SelectedUpgrade upgrade(long id, String name) {
        return selected(unitUpgrade(id, name, UpgradeType.MAGIC_ITEM, 0));
    }

    @Test
    @DisplayName("getUnitDetails: Fellblade sets strength to 10")
    void getUnitDetailsWithFellblade() {
        SelectedUpgrade fellblade = upgrade(2L, "Fellblade");
        fellblade.getUpgrade().addStatModifier(S, SET, 10);
        ArmyUnit armyUnit = armyUnit(null, null, fellblade);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(4L)).thenReturn(armyUnit);

        UnitDetails result = statsCalculatorService().getUnitDetails(4L);

        assertEquals(10, result.s());
        assertEquals(List.of("Fellblade"), result.upgradeNamesList().stream().map(u -> u.name()).toList());
    }

    @Test
    @DisplayName("calculate: Frenzy increases attacks by 1")
    void calculateWithFrenzy() {
        SelectedUpgrade frenzy = upgrade(2L, "Frenzy");
        frenzy.getUpgrade().addStatModifier(A, ADD, 1);

        UnitDetails result = statsCalculatorService().calculate(armyUnit(null, null, frenzy));

        assertEquals(3, result.a());
    }

    @Test
    @DisplayName("calculate: one upgrade can change several stats")
    void calculateWithSeveralModifiers() {
        SelectedUpgrade weapon = upgrade(2L, "Warlock-Augumented Weapon");
        weapon.getUpgrade().addStatModifier(S, ADD, 1);
        weapon.getUpgrade().addStatModifier(A, ADD, 1);

        UnitDetails result = statsCalculatorService().calculate(armyUnit(null, null, weapon));

        assertEquals(5, result.s());
        assertEquals(3, result.a());
    }

    @Test
    @DisplayName("calculate: no save stays empty, armour improves from 'no save'")
    void calculateSavesFromNone() {
        SelectedUpgrade lightArmour = upgrade(2L, "Light Armour");
        lightArmour.getUpgrade().addStatModifier(ARMOUR_SAVE, IMPROVE, 1);

        UnitDetails noUpgrades = statsCalculatorService().calculate(armyUnit(0, 0));
        UnitDetails withArmour = statsCalculatorService().calculate(armyUnit(0, 0, lightArmour));

        assertNull(noUpgrades.basicSave());
        assertNull(noUpgrades.wardSave());
        assertEquals(6, withArmour.basicSave());
    }

    @Test
    @DisplayName("calculate: SET is applied before IMPROVE regardless of selection order")
    void calculateAppliesPhasesInOrder() {
        SelectedUpgrade shield = upgrade(2L, "Shield");
        shield.getUpgrade().addStatModifier(ARMOUR_SAVE, IMPROVE, 1);
        SelectedUpgrade armour = upgrade(3L, "Armour of Destiny");
        armour.getUpgrade().addStatModifier(ARMOUR_SAVE, SET, 5);

        UnitDetails result = statsCalculatorService().calculate(armyUnit(null, null, shield, armour));

        assertEquals(4, result.basicSave());
    }

    @Test
    @DisplayName("calculate: BEST_OF keeps the better ward save")
    void calculateBestOfKeepsBetterWard() {
        SelectedUpgrade talisman = upgrade(2L, "Talisman of Protection");
        talisman.getUpgrade().addStatModifier(WARD_SAVE, BEST_OF, 6);

        UnitDetails result = statsCalculatorService().calculate(armyUnit(null, 4, talisman));

        assertEquals(4, result.wardSave());
    }

    @Test
    @DisplayName("calculate: armour save cannot be better than 2+")
    void calculateCapsSaveAtTwo() {
        SelectedUpgrade armour = upgrade(2L, "Armour of Silvered Steel");
        armour.getUpgrade().addStatModifier(ARMOUR_SAVE, SET, 2);
        SelectedUpgrade shield = upgrade(3L, "Shield");
        shield.getUpgrade().addStatModifier(ARMOUR_SAVE, IMPROVE, 1);

        UnitDetails result = statsCalculatorService().calculate(armyUnit(null, null, armour, shield));

        assertEquals(2, result.basicSave());
    }
}
