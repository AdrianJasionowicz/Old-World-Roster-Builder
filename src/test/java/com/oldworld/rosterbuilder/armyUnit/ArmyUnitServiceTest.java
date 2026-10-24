package com.oldworld.rosterbuilder.armyUnit;

import com.oldworld.rosterbuilder.army.Army;
import com.oldworld.rosterbuilder.exception.BusinessRuleException;
import com.oldworld.rosterbuilder.exception.ForbiddenException;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedQuantityValidator;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unit.UnitType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArmyUnitServiceTest {

    @Mock
    CurrentUserService currentUserService;
    @Mock
    SelectedQuantityValidator selectedQuantityValidator;
    @Spy
    DetachmentValidator detachmentValidator = new DetachmentValidator();
    @InjectMocks
    ArmyUnitService armyUnitService;

    private ArmyUnit coreArmyUnit(int quantity) {
        var unit = new Unit();
        unit.setPointsCostPerUnit(20);
        unit.setMinQuantity(20);
        unit.setUnitType(UnitType.CORE);
        var army = new Army();
        var armyUnit = new ArmyUnit();
        armyUnit.setId(100L);
        armyUnit.setQuantity(quantity);
        armyUnit.setArmy(army);
        armyUnit.setUnit(unit);
        army.getArmyUnitsList().add(armyUnit);
        return armyUnit;
    }

    @Test
    @DisplayName("Change unit size increment")
    void changeUnitSizeIncrementation() {
        var armyUnit = coreArmyUnit(20);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(100L)).thenReturn(armyUnit);

        armyUnitService.changeUnitSize(100L, +1);

        assertEquals(21, armyUnit.getQuantity());
        verify(selectedQuantityValidator).validateArmyUnitQuantity(armyUnit);
    }

    @Test
    @DisplayName("Change unit size decrement")
    void changeUnitSizeDecrementation() {
        var armyUnit = coreArmyUnit(42);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(100L)).thenReturn(armyUnit);

        armyUnitService.changeUnitSize(100L, -21);

        assertEquals(21, armyUnit.getQuantity());
    }

    @Test
    @DisplayName("deleteArmyUnit removes the unit from its army (orphanRemoval deletes the row)")
    void deleteArmyUnit() {
        var armyUnit = coreArmyUnit(20);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(100L)).thenReturn(armyUnit);

        armyUnitService.deleteArmyUnit(100L);

        assertTrue(armyUnit.getArmy().getArmyUnitsList().isEmpty());
    }

    @Test
    @DisplayName("Deleting a regiment also removes its detachments")
    void deleteRegimentWithDetachments() {
        var regiment = coreArmyUnit(20);
        var detachment = detachmentOf(regiment, 5);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(100L)).thenReturn(regiment);

        armyUnitService.deleteArmyUnit(100L);

        assertFalse(regiment.getArmy().getArmyUnitsList().contains(detachment));
        assertTrue(regiment.getArmy().getArmyUnitsList().isEmpty());
    }

    @Test
    @DisplayName("A detachment cannot grow over half of its regiment, and the regiment cannot shrink under twice the detachment")
    void detachmentSize() {
        var regiment = coreArmyUnit(20);
        var detachment = detachmentOf(regiment, 10);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(101L)).thenReturn(detachment);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(100L)).thenReturn(regiment);

        assertThrows(BusinessRuleException.class, () -> armyUnitService.changeUnitSize(101L, 1));
        assertThrows(BusinessRuleException.class, () -> armyUnitService.changeUnitSize(100L, -1));
    }

    private ArmyUnit detachmentOf(ArmyUnit regiment, int quantity) {
        var detachment = new ArmyUnit();
        detachment.setId(101L);
        detachment.setQuantity(quantity);
        detachment.setUnit(regiment.getUnit());
        detachment.setArmy(regiment.getArmy());
        detachment.setParent(regiment);
        regiment.getDetachments().add(detachment);
        regiment.getArmy().getArmyUnitsList().add(detachment);
        return detachment;
    }

    @Test
    void deleteArmyUnitWithAuthException() {
        when(currentUserService.getArmyUnitOwnedByCurrentUser(100L))
                .thenThrow(new ForbiddenException("Access denied"));

        assertThrows(ForbiddenException.class, () -> armyUnitService.deleteArmyUnit(100L));
    }
}
