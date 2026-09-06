package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnitRepository;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.exception.UpgradeNotFoundException;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.model.UpgradeViewCombined;
import com.armybuilderv2.armyBuilderV2.unit.Unit;
import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SelectedUpgradeServiceTest {

    @Mock
    SelectedUpgradeRepository selectedUpgradeRepository;
    @Mock
    ArmyUnitRepository armyUnitRepository;
    @Mock
    SelectedUpgradeMapper selectedUpgradeMapper;
    @Mock
    CurrentUserService currentUserService;
    @Mock
    SelectedUpgradeValidatorService selectedUpgradeValidator;
    @InjectMocks
    SelectedUpgradeService selectedUpgradeService;



    @Test
    void selectUpgrade() {
    }

    @Test
    @DisplayName("Get UpgradeView Happy Path")
    void getUpgradeViewHappyPath() {
        Long armyUnitId = 1L;

        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(armyUnitId);

        Upgrade selectedUpgrade = new Upgrade();
        selectedUpgrade.setId(1L);
        selectedUpgrade.setName("TEST");
        selectedUpgrade.setPointsCost(100.0);
        selectedUpgrade.setUpgradeType(UpgradeType.MAGIC_ITEM);
        selectedUpgrade.setDescription("TEST");

        SelectedUpgrade selectedUpgradeEntity = new SelectedUpgrade();
        selectedUpgradeEntity.setId(10L);
        selectedUpgradeEntity.setUpgrade(selectedUpgrade);

        List<SelectedUpgrade> selectedUpgrades = new ArrayList<>();
        selectedUpgrades.add(selectedUpgradeEntity);
        armyUnit.setSelectedUpgradesList(selectedUpgrades);

        Upgrade notSelectedUpgrade = new Upgrade();
        notSelectedUpgrade.setId(12L);
        notSelectedUpgrade.setName("TEST 2");
        notSelectedUpgrade.setPointsCost(1020.0);
        notSelectedUpgrade.setUpgradeType(UpgradeType.MAGIC_ITEM);
        notSelectedUpgrade.setDescription("TEST 2");

        List<Upgrade> upgrades = new ArrayList<>();
        upgrades.add(selectedUpgrade);
        upgrades.add(notSelectedUpgrade);

        Unit unit = new Unit();
        unit.setUpgradesList(upgrades);
        armyUnit.setUnit(unit);

        when(armyUnitRepository.findById(armyUnitId))
                .thenReturn(Optional.of(armyUnit));

        // when
        List<UpgradeViewCombined> result =
                selectedUpgradeService.getUpgradeView(armyUnitId);

        // then
        assertEquals(2, result.size());

        assertTrue(result.get(0).selected());
        assertFalse(result.get(1).selected());

        assertEquals(1L, result.get(0).id());
        assertEquals("TEST", result.get(0).name());
        assertEquals(100.0, result.get(0).totalCost());
        assertEquals(UpgradeType.MAGIC_ITEM, result.get(0).upgradeType());
        assertEquals("TEST", result.get(0).description());
    }



    @Test
    @DisplayName("Remove upgrade happy path")
    void removeUpgrade() {
        Long armyUnitId = 1L;
        Long upgradeId = 2L;
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(armyUnitId);
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(upgradeId);
        selectedUpgrade.setArmyUnit(armyUnit);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(upgradeId);
        selectedUpgrade.setUpgrade(upgrade);
        List<SelectedUpgrade> selectedUpgrades = new ArrayList<>();
        selectedUpgrades.add(selectedUpgrade);
        doNothing().when(currentUserService).validateArmyAccess(armyUnit.getArmy());
        armyUnit.setSelectedUpgradesList(selectedUpgrades);

        when(armyUnitRepository.findById(armyUnitId)).thenReturn(Optional.of(armyUnit));
        selectedUpgradeService.removeUpgrade(armyUnitId, upgradeId);


        verify(selectedUpgradeRepository).forceDelete(upgradeId);
    }

    @Test
    @DisplayName("Remove upgrade exception: UpgradeNotFoundException ")
    void removeUpgradeWithUpgradeNotFoundException() {
        Long armyUnitId = 1L;
        Long upgradeId = 2L;
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(armyUnitId);
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(upgradeId);
        selectedUpgrade.setArmyUnit(armyUnit);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(upgradeId);
        selectedUpgrade.setUpgrade(upgrade);
        List<SelectedUpgrade> selectedUpgrades = new ArrayList<>();
        selectedUpgrades.add(selectedUpgrade);
        doNothing().when(currentUserService).validateArmyAccess(armyUnit.getArmy());
        armyUnit.setSelectedUpgradesList(selectedUpgrades);

        when(armyUnitRepository.findById(armyUnitId)).thenReturn(Optional.of(armyUnit));


        assertThrows(UpgradeNotFoundException.class, () -> selectedUpgradeService.removeUpgrade(armyUnitId, 10L));
    }

    @Test
    @DisplayName("Remove upgrade exception: ArmyUnitNotFoundException ")
    void removeUpgradeWithArmyUnitNotFoundException() {
        Long armyUnitId = 1L;
        Long upgradeId = 2L;
        when(armyUnitRepository.findById(armyUnitId)).thenReturn(Optional.empty());


        assertThrows(ArmyUnitNotFoundException.class, () -> selectedUpgradeService.removeUpgrade(armyUnitId,upgradeId));
    }
}