package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import com.armybuilderv2.armyBuilderV2.army.Army;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.exception.*;
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

import static com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType.MAGIC_ITEM;
import static com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType.WEAPON_TEAM;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class SelectedUpgradeValidatorServiceTest {

    @InjectMocks
    SelectedUpgradeValidatorService selectedUpgradeValidatorService;



    @Test
    @DisplayName("ValidateWeaponTeams happy path")
    void validateWeaponTeams() {
        ArmyUnit armyUnit = new ArmyUnit();
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(WEAPON_TEAM);
        selectedUpgrade.setUpgrade(upgrade);
        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        selectedUpgradeList.add(selectedUpgrade);
        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        assertDoesNotThrow(() -> selectedUpgradeValidatorService.validateWeaponTeams(armyUnit));
    }

    @Test
    @DisplayName("ValidateWeaponTeams exception: WeaponTeamLimitExceededException")
    void validateWeaponTeamsThrowsWeaponTeamLimitExceededException() {
        ArmyUnit armyUnit = new ArmyUnit();
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(WEAPON_TEAM);
        selectedUpgrade.setUpgrade(upgrade);

        SelectedUpgrade selectedUpgrade2 = new SelectedUpgrade();
        selectedUpgrade2.setId(2L);
        Upgrade upgrade2 = new Upgrade();
        upgrade2.setId(3L);
        upgrade2.setUpgradeType(WEAPON_TEAM);
        selectedUpgrade2.setUpgrade(upgrade2);

        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        selectedUpgradeList.add(selectedUpgrade);
        selectedUpgradeList.add(selectedUpgrade2);
        armyUnit.setSelectedUpgradesList(selectedUpgradeList);

        assertThrows(WeaponTeamLimitExceededException.class, () -> selectedUpgradeValidatorService.validateWeaponTeams(armyUnit));
    }

    @Test
    @DisplayName("validateBattleStandards  happy path")
    void validateBattleStandards() {
        Army army = new Army();
        army.setId(1L);
        ArmyUnit armyUnit = new ArmyUnit();
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(UpgradeType.BSB);
        Unit unit = new Unit();
        unit.setId(1L);
        unit.setUnitType(UnitType.HERO);
        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        selectedUpgrade.setUpgrade(upgrade);
        selectedUpgradeList.add(selectedUpgrade);
        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        List<ArmyUnit> armyUnitList = new ArrayList<>();
        armyUnit.setArmy(army);
        armyUnit.setUnit(unit);
        armyUnitList.add(armyUnit);
        army.setArmyUnitsList(armyUnitList);
        assertDoesNotThrow(() -> selectedUpgradeValidatorService.validateBattleStandards(armyUnit));
    }

    @Test
    @DisplayName("validateBattleStandards exception: BsbLimitExceededException")
    void validateBattleStandardsThrowsBsbLimitExceededException() {

        Army army = new Army();
        army.setId(1L);

        ArmyUnit armyUnit = new ArmyUnit();

        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);

        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(UpgradeType.BSB);

        selectedUpgrade.setUpgrade(upgrade);

        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        selectedUpgradeList.add(selectedUpgrade);

        Unit unit = new Unit();
        unit.setId(1L);
        unit.setUnitType(UnitType.HERO);

        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        armyUnit.setArmy(army);
        armyUnit.setUnit(unit);


        ArmyUnit armyUnit2 = new ArmyUnit();

        SelectedUpgrade selectedUpgrade2 = new SelectedUpgrade();
        selectedUpgrade2.setId(2L);

        Upgrade upgrade2 = new Upgrade();
        upgrade2.setId(3L);
        upgrade2.setUpgradeType(UpgradeType.BSB);

        selectedUpgrade2.setUpgrade(upgrade2);

        List<SelectedUpgrade> selectedUpgradeList2 = new ArrayList<>();
        selectedUpgradeList2.add(selectedUpgrade2);

        Unit unit2 = new Unit();
        unit2.setId(3L);
        unit2.setUnitType(UnitType.HERO);

        armyUnit2.setSelectedUpgradesList(selectedUpgradeList2);
        armyUnit2.setArmy(army);
        armyUnit2.setUnit(unit2);


        List<ArmyUnit> armyUnitList = new ArrayList<>();
        armyUnitList.add(armyUnit);
        armyUnitList.add(armyUnit2);

        army.setArmyUnitsList(armyUnitList);


        assertThrows(
                BsbLimitExceededException.class,
                () -> selectedUpgradeValidatorService.validateBattleStandards(armyUnit)
        );
    }


    @Test
    @DisplayName("validateMagicBanner happy path")
    void validateMagicBannerAndCheckPresence() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(1L);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(UpgradeType.MAGIC_BANNER);
        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);
        selectedUpgrade.setUpgrade(upgrade);
        selectedUpgradeList.add(selectedUpgrade);
        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        assertTrue(selectedUpgradeValidatorService.validateMagicBannerAndCheckPresence(armyUnit));

    }

    @Test
    @DisplayName("validateMagicBanner with exception: MagicBannerLimitExceededException ")
    void validateMagicBannerAndCheckPresenceWithMagicBannerLimitExceededException() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(1L);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(UpgradeType.MAGIC_BANNER);
        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setUpgrade(upgrade);

        Upgrade upgrade2 = new Upgrade();
        upgrade2.setId(23L);
        upgrade2.setUpgradeType(UpgradeType.MAGIC_BANNER);
        SelectedUpgrade selectedUpgrade2 = new SelectedUpgrade();
        selectedUpgrade2.setId(12L);
        selectedUpgrade2.setUpgrade(upgrade2);
        selectedUpgradeList.add(selectedUpgrade);
        selectedUpgradeList.add(selectedUpgrade2);
        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        assertThrows(MagicBannerLimitExceededException.class,()->selectedUpgradeValidatorService.validateMagicBannerAndCheckPresence(armyUnit));

    }

    @Test
    @DisplayName("validateMagicBannerRestrictions happy path")
    void validateMagicBannerRestrictions() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(1L);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(UpgradeType.MAGIC_BANNER);
        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);
        upgrade.setUpgradeType(UpgradeType.MAGIC_BANNER);
        selectedUpgrade.setUpgrade(upgrade);
        selectedUpgradeList.add(selectedUpgrade);
        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        assertDoesNotThrow(() -> selectedUpgradeValidatorService.validateMagicBannerAndCheckPresence(armyUnit));
    }

    @Test
    @DisplayName("validateMagicBannerRestrictions exception: MagicBannerConflictException")
    void validateMagicBannerRestrictionsWithMagicBannerConflictException() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(1L);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(UpgradeType.MAGIC_BANNER);
        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);
        upgrade.setUpgradeType(UpgradeType.MAGIC_BANNER);
        selectedUpgrade.setUpgrade(upgrade);
        selectedUpgradeList.add(selectedUpgrade);

        SelectedUpgrade selectedUpgrade2 = new SelectedUpgrade();
        selectedUpgrade2.setId(3L);
        Upgrade upgrade2 = new Upgrade();
        upgrade2.setId(4L);
        upgrade2.setUpgradeType(UpgradeType.MAGIC_WEAPON);
        selectedUpgrade2.setUpgrade(upgrade2);
        selectedUpgradeList.add(selectedUpgrade2);

        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        assertThrows(MagicBannerConflictException.class, () -> selectedUpgradeValidatorService.validateMagicBannerRestrictions(armyUnit));
    }


    @Test
    @DisplayName("checkLordsAndHeroUpgrades happy path")
    void checkLordsAndHeroUpgrades() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(1L);
        Unit unit = new Unit();
        unit.setId(2L);
        unit.setUnitType(UnitType.HERO);
        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);
        armyUnit.setUnit(unit);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(MAGIC_ITEM);
        upgrade.setPointsCost(49);
        selectedUpgrade.setUpgrade(upgrade);
        selectedUpgradeList.add(selectedUpgrade);
        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        assertDoesNotThrow(()-> selectedUpgradeValidatorService.checkLordsAndHeroUpgrades(armyUnit));
    }

    @Test
    @DisplayName("checkLordsAndHeroUpgrades with exception: LordsUpgradePointsExceededException")
    void checkLordsAndHeroUpgradesWithLordsUpgradePointsExceededException() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(1L);
        Unit unit = new Unit();
        unit.setId(2L);
        unit.setUnitType(UnitType.HERO);
        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);
        armyUnit.setUnit(unit);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(MAGIC_ITEM);
        upgrade.setPointsCost(51);
        selectedUpgrade.setUpgrade(upgrade);
        selectedUpgradeList.add(selectedUpgrade);
        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        assertThrows(LordsUpgradePointsExceededException.class, ()-> selectedUpgradeValidatorService.checkLordsAndHeroUpgrades(armyUnit));
    }


    @Test
    @DisplayName("checkLordsAndHeroUpgrades with exception: LordsUpgradePointsExceededException")
    void checkLordsAndHeroUpgradesWithMoreThanOneItemLordsUpgradePointsExceededException() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(1L);
        Unit unit = new Unit();
        unit.setId(2L);
        unit.setUnitType(UnitType.HERO);
        List<SelectedUpgrade> selectedUpgradeList = new ArrayList<>();
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);
        armyUnit.setUnit(unit);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        upgrade.setUpgradeType(MAGIC_ITEM);
        upgrade.setPointsCost(49);
        selectedUpgrade.setUpgrade(upgrade);
        selectedUpgradeList.add(selectedUpgrade);

        SelectedUpgrade selectedUpgrade2 = new SelectedUpgrade();
        selectedUpgrade2.setId(3L);
        Upgrade upgrade2 = new Upgrade();
        upgrade2.setId(4L);
        upgrade2.setUpgradeType(MAGIC_ITEM);
        upgrade2.setPointsCost(51);
        selectedUpgrade2.setUpgrade(upgrade2);
        selectedUpgradeList.add(selectedUpgrade2);

        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        assertThrows(LordsUpgradePointsExceededException.class, ()-> selectedUpgradeValidatorService.checkLordsAndHeroUpgrades(armyUnit));
    }



    @Test
    @DisplayName("checkUpgradeQuantities happy path")
    void checkUpgradeQuantities() {
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setId(1L);
        SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
        selectedUpgrade.setId(1L);
        Upgrade upgrade = new Upgrade();
        upgrade.setId(2L);
        
    }


    public void checkUpgradeQuantities(ArmyUnit armyUnit, Long upgradeId) {
        SelectedUpgrade selectedUpgrade = armyUnit.getSelectedUpgradesList().stream()
                .filter(su -> su.getUpgrade().getId().equals(upgradeId))
                .findFirst()
                .orElseThrow(() -> new UpgradeNotFoundException(armyUnit.getUnit().getUnitType().toString()));
        switch (selectedUpgrade.getUpgrade().getUpgradeType()) {
            case UNIT_EQUIPMENT:
                selectedUpgrade.setQuantity(armyUnit.getQuantity());
                break;
            default:
                selectedUpgrade.setQuantity(1);
        }
    }

    @Test
    void checkAllUpgrades() {
    }
}