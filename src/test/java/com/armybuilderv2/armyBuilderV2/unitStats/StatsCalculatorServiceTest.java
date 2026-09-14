package com.armybuilderv2.armyBuilderV2.unitStats;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnitRepository;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgrade;
import com.armybuilderv2.armyBuilderV2.unit.Unit;
import com.armybuilderv2.armyBuilderV2.unitStats.model.CurrentStats;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitDetails;
import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class StatsCalculatorServiceTest {

    @Mock
    ArmyUnitRepository armyUnitRepository;
    @Mock
    CurrentUserService currentUserService;
    @InjectMocks
    StatsCalculatorService statsCalculatorService;


    @Test
    @DisplayName("getUnitDetails: Fellblade sets strength to 10")
    void getUnitDetailsWithFellblade() {

        UnitStats unitStats = new UnitStats();
        unitStats.setM(7);
        unitStats.setWs(4);
        unitStats.setBs(4);
        unitStats.setS(4);
        unitStats.setT(4);
        unitStats.setW(2);
        unitStats.setI(5);
        unitStats.setA(2);
        unitStats.setLd(8);
        unitStats.setBasicSave(null);
        unitStats.setWardSave(null);

        Unit unit = new Unit();
        unit.setId(1L);
        unit.setUnitStats(unitStats);

        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setName("Fellblade");

        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(3L);
        selectedUpgrade.setUpgrade(upgrade);

        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(4L);
        armyUnit.setUnit(unit);
        armyUnit.setSelectedUpgradesList(
                new ArrayList<>(List.of(selectedUpgrade))
        );

        when(armyUnitRepository.findById(4L))
                .thenReturn(Optional.of(armyUnit));

        doNothing()
                .when(currentUserService)
                .validateArmyAccess(armyUnit.getArmy());

        UnitDetails result = statsCalculatorService.getUnitDetails(4L);

        assertEquals(10, result.s());
    }


    @Test
    @DisplayName("getUnitDetails: Frenzy increases attacks by 1")
    void getUnitDetailsWithFrenzy() {

        UnitStats unitStats = new UnitStats();
        unitStats.setM(7);
        unitStats.setWs(4);
        unitStats.setBs(4);
        unitStats.setS(4);
        unitStats.setT(4);
        unitStats.setW(2);
        unitStats.setI(5);
        unitStats.setA(2);
        unitStats.setLd(8);
        unitStats.setBasicSave(null);
        unitStats.setWardSave(null);

        Unit unit = new Unit();
        unit.setId(1L);
        unit.setUnitStats(unitStats);

        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setName("Frenzy");

        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(3L);
        selectedUpgrade.setUpgrade(upgrade);

        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(4L);
        armyUnit.setUnit(unit);
        armyUnit.setSelectedUpgradesList(
                new ArrayList<>(List.of(selectedUpgrade))
        );

        when(armyUnitRepository.findById(4L))
                .thenReturn(Optional.of(armyUnit));

        doNothing()
                .when(currentUserService)
                .validateArmyAccess(armyUnit.getArmy());

        UnitDetails result = statsCalculatorService.getUnitDetails(4L);

        assertEquals(3, result.a());
    }
}