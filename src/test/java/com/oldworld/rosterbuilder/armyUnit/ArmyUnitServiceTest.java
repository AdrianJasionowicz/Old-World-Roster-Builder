package com.oldworld.rosterbuilder.armyUnit;

import com.oldworld.rosterbuilder.army.Army;
import com.oldworld.rosterbuilder.army.ArmyMapper;
import com.oldworld.rosterbuilder.army.Faction;
import com.oldworld.rosterbuilder.army.model.ArmyView;
import com.oldworld.rosterbuilder.exception.BusinessRuleException;
import com.oldworld.rosterbuilder.exception.ForbiddenException;
import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedQuantityValidator;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unit.UnitFaction;
import com.oldworld.rosterbuilder.unit.UnitRepository;
import com.oldworld.rosterbuilder.unit.UnitType;
import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import com.oldworld.rosterbuilder.upgrade.Upgrade;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArmyUnitServiceTest {

    @Mock
    CurrentUserService currentUserService;
    @Mock
    SelectedQuantityValidator selectedQuantityValidator;
    @Spy
    DetachmentValidator detachmentValidator = new DetachmentValidator();
    @Mock
    ArmyUnitRepository armyUnitRepository;
    @Mock
    UnitRepository unitRepository;
    @Mock
    ArmyMapper armyMapper;
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

    private Army empireArmy() {
        var army = new Army();
        army.setId(1L);
        army.setFaction(Faction.EMPIRE);
        return army;
    }

    private Unit catalogUnit(long id, UnitFaction faction, boolean canHaveDetachments, boolean canBeDetachment) {
        var unit = new Unit();
        unit.setId(id);
        unit.setName("Unit " + id);
        unit.setUnitFaction(faction);
        unit.setUnitType(UnitType.CORE);
        unit.setMinQuantity(10);
        unit.setCanHaveDetachments(canHaveDetachments);
        unit.setCanBeDetachment(canBeDetachment);
        unit.addUnitUpgrade(new UnitUpgrade(unit, new Upgrade("Hand weapon", UpgradeType.FREE_UPGRADE, null), 0));
        unit.addUnitUpgrade(new UnitUpgrade(unit, new Upgrade("Shield", UpgradeType.UNIT_EQUIPMENT, null), 1));
        when(unitRepository.findById(id)).thenReturn(Optional.of(unit));
        return unit;
    }

    @Test
    @DisplayName("addArmyUnit adds the unit at its minimum size with its free upgrades")
    void addArmyUnit() {
        var army = empireArmy();
        var unit = catalogUnit(7L, UnitFaction.EMPIRE, false, false);
        var view = new ArmyView(1L, null, null, null, Faction.EMPIRE, List.of(), List.of());
        when(currentUserService.getArmyOwnedByCurrentUser(1L)).thenReturn(army);
        when(armyMapper.makeView(army)).thenReturn(view);

        assertSame(view, armyUnitService.addArmyUnit(1L, 7L));

        var armyUnit = army.getArmyUnitsList().getFirst();
        assertSame(unit, armyUnit.getUnit());
        assertEquals(10, armyUnit.getQuantity());
        assertEquals(List.of("Hand weapon"), armyUnit.getSelectedUpgradesList().stream().map(selected -> selected.getUpgrade().getName()).toList());
        verify(selectedQuantityValidator).recalculateQuantityAndCost(armyUnit);
        verify(armyUnitRepository).save(armyUnit);
    }

    @Test
    @DisplayName("addArmyUnit rejects a unit of another faction")
    void addArmyUnitOtherFaction() {
        var army = empireArmy();
        catalogUnit(7L, UnitFaction.SKAVEN, false, false);
        when(currentUserService.getArmyOwnedByCurrentUser(1L)).thenReturn(army);

        var exception = assertThrows(BusinessRuleException.class, () -> armyUnitService.addArmyUnit(1L, 7L));

        assertEquals("Factions do not match", exception.getMessage());
        assertTrue(army.getArmyUnitsList().isEmpty());
        verify(armyUnitRepository, never()).save(any());
    }

    @Test
    @DisplayName("addArmyUnit of a missing catalog unit is not found")
    void addArmyUnitNotFound() {
        when(currentUserService.getArmyOwnedByCurrentUser(1L)).thenReturn(empireArmy());
        when(unitRepository.findById(7L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> armyUnitService.addArmyUnit(1L, 7L));
        verify(armyUnitRepository, never()).save(any());
    }

    @Test
    @DisplayName("addArmyUnit to another user's army is forbidden")
    void addArmyUnitForbidden() {
        when(currentUserService.getArmyOwnedByCurrentUser(1L)).thenThrow(new ForbiddenException("Access denied"));

        assertThrows(ForbiddenException.class, () -> armyUnitService.addArmyUnit(1L, 7L));
        verifyNoInteractions(unitRepository, armyUnitRepository);
    }

    @Test
    @DisplayName("addDetachment adds a detachment of 5 models linked to its regiment")
    void addDetachment() {
        var army = empireArmy();
        var regiment = regimentIn(army, 20);
        catalogUnit(8L, UnitFaction.EMPIRE, false, true);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(100L)).thenReturn(regiment);

        armyUnitService.addDetachment(100L, 8L);

        var detachment = regiment.getDetachments().getFirst();
        assertSame(regiment, detachment.getParent());
        assertEquals(5, detachment.getQuantity());
        assertTrue(army.getArmyUnitsList().contains(detachment));
        verify(armyMapper).makeView(army);
    }

    @Test
    @DisplayName("addDetachment rejects a unit that cannot be a detachment")
    void addDetachmentWrongUnit() {
        var army = empireArmy();
        var regiment = regimentIn(army, 20);
        catalogUnit(8L, UnitFaction.EMPIRE, false, false);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(100L)).thenReturn(regiment);

        assertThrows(BusinessRuleException.class, () -> armyUnitService.addDetachment(100L, 8L));
        assertTrue(regiment.getDetachments().isEmpty());
        verify(armyUnitRepository, never()).save(any());
    }

    @Test
    @DisplayName("addDetachment to a missing regiment is not found")
    void addDetachmentNotFound() {
        when(currentUserService.getArmyUnitOwnedByCurrentUser(100L)).thenThrow(new NotFoundException("Army unit not found with id: 100"));

        assertThrows(NotFoundException.class, () -> armyUnitService.addDetachment(100L, 8L));
        verifyNoInteractions(unitRepository, armyUnitRepository);
    }

    @Test
    @DisplayName("changeUnitSize of a unit with a fixed size is rejected before the size changes")
    void changeUnitSizeFixedSize() {
        var armyUnit = coreArmyUnit(1);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(100L)).thenReturn(armyUnit);
        doThrow(new BusinessRuleException("Size of this unit cannot be changed")).when(selectedQuantityValidator).checkIfUnitSizeCanBeChanged(armyUnit);

        assertThrows(BusinessRuleException.class, () -> armyUnitService.changeUnitSize(100L, 1));
        assertEquals(1, armyUnit.getQuantity());
    }

    @Test
    @DisplayName("deleteArmyUnit of a detachment removes it from its regiment too")
    void deleteDetachment() {
        var regiment = coreArmyUnit(20);
        var detachment = detachmentOf(regiment, 5);
        when(currentUserService.getArmyUnitOwnedByCurrentUser(101L)).thenReturn(detachment);

        armyUnitService.deleteArmyUnit(101L);

        assertTrue(regiment.getDetachments().isEmpty());
        assertEquals(List.of(regiment), regiment.getArmy().getArmyUnitsList());
    }

    private ArmyUnit regimentIn(Army army, int quantity) {
        var unit = new Unit();
        unit.setName("Swordsmen");
        unit.setUnitFaction(UnitFaction.EMPIRE);
        unit.setCanHaveDetachments(true);
        var regiment = new ArmyUnit();
        regiment.setId(100L);
        regiment.setUnit(unit);
        regiment.setArmy(army);
        regiment.setQuantity(quantity);
        army.getArmyUnitsList().add(regiment);
        return regiment;
    }
}
