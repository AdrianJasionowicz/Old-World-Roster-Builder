package com.armybuilderv2.armyBuilderV2.unitStats;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnitRepository;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgrade;
import com.armybuilderv2.armyBuilderV2.unitStats.model.CurrentStats;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitDetails;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UpgradeName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsCalculatorService {

    private final ArmyUnitRepository armyUnitRepository;
    private final CurrentUserService currentUserService;
    private final StatsCalculatorValidatorService statsCalculatorValidatorService;

    public StatsCalculatorService(ArmyUnitRepository armyUnitRepository, CurrentUserService currentUserService, StatsCalculatorValidatorService statsCalculatorValidatorService) {
        this.armyUnitRepository = armyUnitRepository;
        this.currentUserService = currentUserService;
        this.statsCalculatorValidatorService = statsCalculatorValidatorService;
    }

    ArmyUnit getArmyUnit(Long armyUnitId) {
            ArmyUnit armyUnit = armyUnitRepository.findById(armyUnitId)
                    .orElseThrow(() -> new ArmyUnitNotFoundException("Army unit not found with id: " + armyUnitId));
            currentUserService.validateArmyAccess(armyUnit.getArmy());
            return armyUnit;
    }

    public UnitDetails getUnitDetails(Long armyUnitId) {
        ArmyUnit armyUnit = getArmyUnit(armyUnitId);
        CurrentStats currentStats = createCurrentStats(armyUnit);
        applyArmourEffects(armyUnit, currentStats);
        applyWeaponEffects(armyUnit, currentStats);
        applySkillsEffects(armyUnit, currentStats);
        currentStats = statsCalculatorValidatorService.validateSaveToRules(currentStats);

        List<UpgradeName> upgradeNamesList = armyUnit.getSelectedUpgradesList().stream()
                .map(u -> new UpgradeName(u.getUpgrade().getName()))
                .toList();
        return createUnitDetailsFromCurrentStats(currentStats, upgradeNamesList);
    }

    UnitDetails createUnitDetailsFromCurrentStats(CurrentStats currentStats, List<UpgradeName> upgradeNamesList) {
        return new UnitDetails(currentStats.getM(), currentStats.getWs(), currentStats.getBs(), currentStats.getS(), currentStats.getT(), currentStats.getW(), currentStats.getI(), currentStats.getA(), currentStats.getLd(), currentStats.getBasicSave(), currentStats.getWardSave(), upgradeNamesList);
    }

    CurrentStats createCurrentStats(ArmyUnit armyUnit) {
        return new CurrentStats(
                armyUnit.getUnit().getUnitStats().getM(),
                armyUnit.getUnit().getUnitStats().getWs(),
                armyUnit.getUnit().getUnitStats().getBs(),
                armyUnit.getUnit().getUnitStats().getS(),
                armyUnit.getUnit().getUnitStats().getT(),
                armyUnit.getUnit().getUnitStats().getW(),
                armyUnit.getUnit().getUnitStats().getI(),
                armyUnit.getUnit().getUnitStats().getA(),
                armyUnit.getUnit().getUnitStats().getLd(),
                armyUnit.getUnit().getUnitStats().getBasicSave(),
                armyUnit.getUnit().getUnitStats().getWardSave()
        );
    }

    void applyArmourEffects(ArmyUnit armyUnit, CurrentStats currentStats) {
        List<SelectedUpgrade> selectedUpgrades = armyUnit.getSelectedUpgradesList();




        Integer baseArmour = statsCalculatorValidatorService.safeCheckCurrentSave(currentStats.getBasicSave());
        Integer ward = statsCalculatorValidatorService.safeCheckCurrentSave(currentStats.getWardSave());


        for (SelectedUpgrade upgrade : selectedUpgrades) {
            switch (upgrade.getUpgrade().getName()) {
                case "Light Armour":
                    baseArmour = statsCalculatorValidatorService.safeCalculate(baseArmour, 1);
                    break;
                case "Heavy Armour":
                    baseArmour = statsCalculatorValidatorService.safeCalculate(baseArmour, 2);
                    break;
                case "Armour of Destiny":
                    baseArmour = 5;
                    ward = Math.min(ward, 4);
                    break;
                case "Armour of Silvered Steel":
                    baseArmour = 2;
                    break;
                case "Armour of Fortune":
                    baseArmour = 5;
                    ward = Math.min(ward, 5);
                    break;
                case "Glittering Scales":
                    baseArmour = 6;
                    break;
                case "Gamblers Armour":
                    baseArmour = 5;
                    ward = Math.min(ward, 6);
                    break;
            }
        }

        for (SelectedUpgrade upgrade : selectedUpgrades) {
            switch (upgrade.getUpgrade().getName()) {
                case "Shield":
                case "Shields":
                    baseArmour = statsCalculatorValidatorService.safeCalculate(baseArmour, 1);
                    ward = statsCalculatorValidatorService.safeCalculate(ward, 1);
                    break;
                case "Tricksters helm":
                case "Helm of Discord":
                case "Dragonhelm":
                    baseArmour = statsCalculatorValidatorService.safeCalculate(baseArmour, 1);
                    break;
                case "Charmed Shield", "Shield of Ptolos", "Shield of Distraction":
                    baseArmour = statsCalculatorValidatorService.safeCalculate(baseArmour,1);
                    ward = Math.min(ward, 6);
                    break;
                case "Enchanted Shield":
                    baseArmour = statsCalculatorValidatorService.safeCalculate(baseArmour,2);
                    ward = Math.min(ward, 6);
                    break;
                case "Spellshield":
                    baseArmour = statsCalculatorValidatorService.safeCalculate(baseArmour,1);
                    break;
                case "Warpstone armour", "Worlds Edge Armour":
                    baseArmour = 4;
                    break;
            }
        }

        for (SelectedUpgrade upgrade : selectedUpgrades) {
            switch (upgrade.getUpgrade().getName()) {
                case "Talisman of Preservation":
                    ward = Math.min(ward, 4);
                    break;
                case "Talisman of Endurance", "Ward save (5+)":
                    ward = Math.min(ward, 5);
                    break;
                case "Foul Pendant":
                    ward = Math.min(ward, 5);
                    break;
                case "Talisman of Protection":
                    ward = Math.min(ward, 6);
                    break;
            }
        }

        for (SelectedUpgrade upgrade : selectedUpgrades) {
            switch (upgrade.getUpgrade().getName()) {
                case "Rat Ogre Bonebreaker":
                case "Great Pox Rat":
                case "War-litter":
                    baseArmour = statsCalculatorValidatorService.safeCalculate(baseArmour,1);
                    break;
                case "Screaming Bell":
                    ward = Math.min(ward, 4);
                    break;
            }
        }
        currentStats.setBasicSave(baseArmour == 7 ? null : baseArmour);
        currentStats.setWardSave(ward == 7 ? null : ward);
    }

    void applyWeaponEffects(ArmyUnit armyUnit, CurrentStats currentStats) {
        for (SelectedUpgrade upgrade : armyUnit.getSelectedUpgradesList()) {
            switch (upgrade.getUpgrade().getName()) {
                case "Fellblade":
                    currentStats.setS(10);
                    break;
                case "Warlock-Augumented Weapon":
                    currentStats.setS(currentStats.getS() + 1);
                    currentStats.setA(currentStats.getA() + 1);
                    break;
                case "Blade of Corruption", "Halberd", "Dwarfbane", "Sword of Might":
                    currentStats.setS(currentStats.getS() + 1);
                    break;
                case "Warlock Optics":
                    currentStats.setBs(currentStats.getBs() + 1);
                    break;
                case "Great weapon", "Ogre Blade":
                    currentStats.setS(currentStats.getS() + 2);
                    break;
                case "Giant Blade":
                    currentStats.setBs(currentStats.getS() + 3);
                    break;
                case "Sword of Bloodshed":
                    currentStats.setA(currentStats.getA() + 3);
                    break;
                case "Sword of Strife":
                    currentStats.setA(currentStats.getA() + 2);
                    break;
                case "Fencers Blades":
                    currentStats.setWs(10);
                    break;
                case "Sword of Battle":
                    currentStats.setA(currentStats.getA() + 1);
                    break;
                case "Gold Sigil Sword":
                    currentStats.setI(10);
                    break;
            }
        }
    }

    void applySkillsEffects(ArmyUnit armyUnit, CurrentStats currentStats) {
        for (SelectedUpgrade upgrade : armyUnit.getSelectedUpgradesList()) {
            switch (upgrade.getUpgrade().getName()) {
                case "Frenzy":
                    currentStats.setA(currentStats.getA() + 1);
                    break;
                case "Dodge (6+)":
                    currentStats.setWardSave(6);
                    break;
                case "Dodge (4+)":
                    currentStats.setWardSave(4);
                    break;
                case "Dodge (5+)":
                    currentStats.setWardSave(5);
                    break;
                case "Armour Save(4+)":
                    currentStats.setBasicSave(4);
                    break;
            }
        }
    }


}