package com.armybuilderv2.armyBuilderV2.armyUnit;

import com.armybuilderv2.armyBuilderV2.army.Army;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgradeValidatorService;
import com.armybuilderv2.armyBuilderV2.unit.Unit;
import com.armybuilderv2.armyBuilderV2.unit.UnitType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArmyUnitServiceTest {

    @Mock
    ArmyUnitRepository armyUnitRepository;
    @Mock
    SelectedUpgradeValidatorService selectedUpgradeValidatorService;
    @InjectMocks
    ArmyUnitService armyUnitService;
    @Mock
    CurrentUserService currentUserService;


    @Test
    @DisplayName("Change unit size increment")
    void changeUnitSizeIncrementation() {
        //given

        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(100L);
        armyUnit.setQuantity(20);
        Army army = new Army();
        armyUnit.setArmy(army);
        Unit unit = new Unit();
        unit.setPointsCostPerUnit(20);
        unit.setMinQuantity(20);
        unit.setUpgradesList(new ArrayList<>());
        unit.setUnitType(UnitType.CORE);
        armyUnit.setUnit(unit);

        //when
        when(armyUnitRepository.findById(100L))
                .thenReturn(Optional.of(armyUnit));
        doNothing().when(currentUserService)
                .validateArmyAccess(armyUnit.getArmy());
        doNothing().when(selectedUpgradeValidatorService)
                .checkAllUpgrades(armyUnit);
        //then
        armyUnitService.changeUnitSize(100L,+1);
        assertEquals(21, armyUnit.getQuantity());
    }

    @Test
    @DisplayName("Change unit size decrement")
    void changeUnitSizeDecrementation() {
        //given

        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(100L);
        armyUnit.setQuantity(42);
        Army army = new Army();
        armyUnit.setArmy(army);
        Unit unit = new Unit();
        unit.setPointsCostPerUnit(20);
        unit.setMinQuantity(20);
        unit.setUpgradesList(new ArrayList<>());
        unit.setUnitType(UnitType.CORE);
        armyUnit.setUnit(unit);

        //when
        when(armyUnitRepository.findById(100L))
                .thenReturn(Optional.of(armyUnit));
        doNothing().when(currentUserService)
                .validateArmyAccess(armyUnit.getArmy());
        doNothing().when(selectedUpgradeValidatorService)
                .checkAllUpgrades(armyUnit);
        //then
        armyUnitService.changeUnitSize(100L,-21);
        assertEquals(21, armyUnit.getQuantity());
    }

    @Test
    @DisplayName("Change unit size should throw error")
    void changeUnitSizeWithError() {
    }

    @Test
    void deleteArmyUnit() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(100L);
        armyUnit.setQuantity(20);
        when(armyUnitRepository.findById(100L)).thenReturn(Optional.of(armyUnit));
        doNothing().when(currentUserService)
                .validateArmyAccess(armyUnit.getArmy());
        armyUnitService.deleteArmyUnit(100L);
        verify(armyUnitRepository).delete(armyUnit);
    }
}