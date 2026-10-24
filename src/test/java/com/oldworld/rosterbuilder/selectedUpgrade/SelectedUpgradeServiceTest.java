package com.oldworld.rosterbuilder.selectedUpgrade;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.exception.BusinessRuleException;
import com.oldworld.rosterbuilder.exception.ConflictException;
import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.oldworld.rosterbuilder.support.UpgradeFixtures.selected;
import static com.oldworld.rosterbuilder.support.UpgradeFixtures.unitUpgrade;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SelectedUpgradeServiceTest {

    @Mock
    CurrentUserService currentUserService;
    @Mock
    SelectedUpgradeValidatorService selectedUpgradeValidatorService;
    @Mock
    SelectedQuantityValidator selectedQuantityValidator;
    @InjectMocks
    SelectedUpgradeService selectedUpgradeService;

    private ArmyUnit armyUnitWithAvailable(UnitUpgrade... unitUpgrades) {
        var unit = new Unit();
        unit.setUnitUpgrades(new ArrayList<>(List.of(unitUpgrades)));
        var armyUnit = new ArmyUnit();
        armyUnit.setId(1L);
        armyUnit.setUnit(unit);
        return armyUnit;
    }

    @Test
    @DisplayName("selectUpgrade adds the upgrade and recalculates the cost")
    void selectUpgrade() {
        var shield = unitUpgrade(1L, "Shield", UpgradeType.UNIT_EQUIPMENT, 1);
        var armyUnit = armyUnitWithAvailable(shield);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(1L)).thenReturn(armyUnit);

        selectedUpgradeService.selectUpgrade(1L, 1L);

        assertEquals(1, armyUnit.getSelectedUpgradesList().size());
        assertTrue(armyUnit.getSelectedUpgradesList().getFirst().isFor(1L));
        verify(selectedQuantityValidator).recalculateQuantityAndCost(armyUnit);
    }

    @Test
    @DisplayName("selectUpgrade leaves the unit untouched when a rule rejects the upgrade")
    void selectUpgradeRejectedByRule() {
        var weaponTeam = unitUpgrade(1L, "Ratling Gun", UpgradeType.WEAPON_TEAM, 55);
        var armyUnit = armyUnitWithAvailable(weaponTeam);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(1L)).thenReturn(armyUnit);
        doThrow(new BusinessRuleException("rule")).when(selectedUpgradeValidatorService).validateSelection(armyUnit, weaponTeam);

        assertThrows(BusinessRuleException.class, () -> selectedUpgradeService.selectUpgrade(1L, 1L));
        assertTrue(armyUnit.getSelectedUpgradesList().isEmpty());
        verifyNoInteractions(selectedQuantityValidator);
    }

    @Test
    @DisplayName("selectUpgrade twice is a conflict")
    void selectUpgradeAlreadySelected() {
        var shield = unitUpgrade(1L, "Shield", UpgradeType.UNIT_EQUIPMENT, 1);
        var armyUnit = armyUnitWithAvailable(shield);
        armyUnit.getSelectedUpgradesList().add(selected(shield));
        when(currentUserService.getArmyUnitOwnedByCurrentUser(1L)).thenReturn(armyUnit);

        assertThrows(ConflictException.class, () -> selectedUpgradeService.selectUpgrade(1L, 1L));
    }

    @Test
    @DisplayName("selectUpgrade of an upgrade the unit cannot take is not found")
    void selectUpgradeNotAvailable() {
        var armyUnit = armyUnitWithAvailable(unitUpgrade(1L, "Shield", UpgradeType.UNIT_EQUIPMENT, 1));
        when(currentUserService.getArmyUnitOwnedByCurrentUser(1L)).thenReturn(armyUnit);

        assertThrows(NotFoundException.class, () -> selectedUpgradeService.selectUpgrade(1L, 2L));
    }

    @Test
    @DisplayName("getUpgradeView lists available upgrades and marks selected ones")
    void getUpgradeView() {
        var selectedOne = unitUpgrade(1L, "TEST", UpgradeType.MAGIC_ITEM, 100.0);
        var notSelected = unitUpgrade(12L, "TEST 2", UpgradeType.MAGIC_ITEM, 1020.0);
        var armyUnit = armyUnitWithAvailable(selectedOne, notSelected);
        armyUnit.getSelectedUpgradesList().add(selected(selectedOne));
        when(currentUserService.getArmyUnitOwnedByCurrentUser(1L)).thenReturn(armyUnit);

        var result = selectedUpgradeService.getUpgradeView(1L);

        assertEquals(2, result.size());
        assertTrue(result.get(0).selected());
        assertFalse(result.get(1).selected());
        assertEquals(1L, result.get(0).id());
        assertEquals(100.0, result.get(0).totalCost());
    }

    @Test
    @DisplayName("removeUpgrade removes it from the unit and recalculates the cost")
    void removeUpgrade() {
        var shield = unitUpgrade(2L, "Shield", UpgradeType.UNIT_EQUIPMENT, 1);
        var armyUnit = armyUnitWithAvailable(shield);
        armyUnit.getSelectedUpgradesList().add(selected(shield));
        when(currentUserService.getArmyUnitOwnedByCurrentUser(1L)).thenReturn(armyUnit);

        selectedUpgradeService.removeUpgrade(1L, 2L);

        assertTrue(armyUnit.getSelectedUpgradesList().isEmpty());
        verify(selectedQuantityValidator).recalculateQuantityAndCost(armyUnit);
    }

    @Test
    @DisplayName("removeUpgrade of an upgrade that is not selected is not found")
    void removeUpgradeNotSelected() {
        var armyUnit = armyUnitWithAvailable(unitUpgrade(2L, "Shield", UpgradeType.UNIT_EQUIPMENT, 1));
        when(currentUserService.getArmyUnitOwnedByCurrentUser(1L)).thenReturn(armyUnit);

        assertThrows(NotFoundException.class, () -> selectedUpgradeService.removeUpgrade(1L, 2L));
    }
}
