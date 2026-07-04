package com.armybuilderv2.armyBuilderV2.unitStats;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnitRepository;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgrade;
import com.armybuilderv2.armyBuilderV2.unitStats.model.CurrentStats;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitDetails;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UpgradeName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsCalculatorService {

    private final ArmyUnitRepository armyUnitRepository;

    public StatsCalculatorService(ArmyUnitRepository armyUnitRepository) {
        this.armyUnitRepository = armyUnitRepository;
    }


    public UnitDetails getUnitDetails(Long id) {
        ArmyUnit armyUnit = armyUnitRepository.findById(id).orElseThrow(() -> new ArmyUnitNotFoundException("Unit not found with id: " + id));
        CurrentStats currentStats = createStatsDtoFromArmyUnit(armyUnit);
        currentStats = setArmourStats(armyUnit, currentStats);
        currentStats = setWeaponsStats(armyUnit, currentStats);
        currentStats = setSkillsStats(armyUnit, currentStats);

        List<UpgradeName> upgradeNamesList = armyUnit.getSelectedUpgradesList().stream()
                .map(u -> new UpgradeName(u.getUpgrade().getName()))
                .toList();

        UnitDetails unitDetails = createUnitDetailsFromStatsDTO(currentStats, upgradeNamesList);
        return unitDetails;
    }

    protected UnitDetails createUnitDetailsFromStatsDTO(CurrentStats currentStats, List<UpgradeName> upgradeNamesList) {
        return new UnitDetails(currentStats.getM(), currentStats.getWs(), currentStats.getBs(), currentStats.getS(), currentStats.getT(), currentStats.getW(), currentStats.getI(), currentStats.getA(), currentStats.getLd(), currentStats.getBasicSave(), currentStats.getWardSave(), upgradeNamesList);
    }

    protected CurrentStats createStatsDtoFromArmyUnit(ArmyUnit armyUnit) {
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

    public CurrentStats setArmourStats(ArmyUnit armyUnit, CurrentStats currentStats) {
        List<SelectedUpgrade> selectedUpgrades = armyUnit.getSelectedUpgradesList();

        int baseArmour = 7;
        int ward = 7;

        for (SelectedUpgrade upgrade : selectedUpgrades) {
            switch (upgrade.getUpgrade().getName()) {
                case "Light Armour":
                    baseArmour = Math.min(baseArmour, 6);
                    break;
                case "Heavy Armour":
                    baseArmour = Math.min(baseArmour, 5);
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
                    baseArmour = safeCalculate(baseArmour, 1);
                    ward = safeCalculate(ward, 1);
                    break;
                case "Tricksters helm":
                case "Helm of Discord":
                case "Dragonhelm":
                    baseArmour = safeCalculate(baseArmour, 1);
                    break;
                case "Charmed Shield":
                    baseArmour = safeDecrement(baseArmour, 1, 6);
                    ward = Math.min(ward, 6);
                    break;
                case "Enchanted Shield":
                    baseArmour = safeDecrement(baseArmour, 2, 6);
                    ward = Math.min(ward, 6);
                    break;
                case "Shield of Ptolos":
                    baseArmour = safeDecrement(baseArmour, 1, 6);
                    ward = Math.min(ward, 6);
                    break;
                case "Shield of Distraction":
                    baseArmour = safeDecrement(baseArmour, 1, 6);
                    ward = Math.min(ward, 6);
                    break;
                case "Spellshield":
                    baseArmour = safeDecrement(baseArmour, 1, 6);
                    break;
                case "Warpstone armour":
                    baseArmour = 4;
                    break;
                case "Worlds Edge Armour":
                    baseArmour = 4;
                    break;
            }
        }

        for (SelectedUpgrade upgrade : selectedUpgrades) {
            switch (upgrade.getUpgrade().getName()) {
                case "Talisman of Preservation":
                    ward = Math.min(ward, 4);
                    break;
                case "Talisman of Endurance":
                    ward = Math.min(ward, 5);
                    break;
                case "Foul Pendant":
                    ward = Math.min(ward, 5);
                    break;
                case "Ward save (5+)":
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
                    baseArmour = safeDecrement(baseArmour, 1, 6);
                    break;
                case "Screaming Bell":
                    ward = Math.min(ward, 4);
                    break;
            }
        }
        currentStats.setBasicSave(baseArmour == 7 ? null : baseArmour);
        currentStats.setWardSave(ward == 7 ? null : ward);
        return currentStats;
    }

    public CurrentStats setWeaponsStats(ArmyUnit armyUnit, CurrentStats currentStats) {
        for (SelectedUpgrade upgrade : armyUnit.getSelectedUpgradesList()) {
            switch (upgrade.getUpgrade().getName()) {
                case "Fellblade":
                    currentStats.setS(10);
                    break;
                case "Warlock-Augumented Weapon":
                    currentStats.setS(currentStats.getS() + 1);
                    currentStats.setA(currentStats.getA() + 1);
                    break;
                case "Blade of Corruption":
                    currentStats.setS(currentStats.getS() + 1);
                    break;
                case "Dwarfbane":
                    currentStats.setS(currentStats.getS() + 1);
                    break;
                case "Warlock Optics":
                    currentStats.setBs(currentStats.getBs() + 1);
                    break;
                case "Halberd":
                    currentStats.setS(currentStats.getS() + 1);
                    break;
                case "Great weapon":
                    currentStats.setS(currentStats.getS() + 2);
                    break;
                case "Giant Blade":
                    currentStats.setBs(currentStats.getBs() + 3);
                    break;
                case "Sword of Bloodshed":
                    currentStats.setA(currentStats.getA() + 3);
                    break;
                case "Ogre Blade":
                    currentStats.setS(currentStats.getS() + 2);
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
                case "Sword of Might":
                    currentStats.setS(currentStats.getS() + 1);
                    break;
                case "Gold Sigil Sword":
                    currentStats.setI(10);
                    break;
            }
        }
        return currentStats;
    }

    public CurrentStats setSkillsStats(ArmyUnit armyUnit, CurrentStats currentStats) {
        for (SelectedUpgrade upgrade : armyUnit.getSelectedUpgradesList()) {
            switch (upgrade.getUpgrade().getName()) {
                case "Frenzy":
                    currentStats.setA(currentStats.getA() + 1);
                    break;
                case "Dodge (6+)":
                    currentStats.setWardSave(6);
                    break;
                case "Armour Save(4+)":
                    currentStats.setBasicSave(4);
                    break;
            }
        }
        return currentStats;
    }

    private int safeDecrement(Integer value, int dec, int defaultValue) {
        return (value == null ? defaultValue : value) - dec;
    }

    private int safeCalculate(Integer baseValue, int decValue) {
        if (baseValue == null) return 6;
        return baseValue - decValue;
    }

}
