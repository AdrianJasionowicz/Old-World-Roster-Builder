package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import com.armybuilderv2.armyBuilderV2.army.Army;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.exception.*;
import com.armybuilderv2.armyBuilderV2.unit.UnitType;
import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType.WEAPON_TEAM;

@Service
public class SelectedUpgradeValidatorService {


    public void validateWeaponTeams(ArmyUnit armyUnit) {
        long weaponTeams = armyUnit.getSelectedUpgradesList().stream()
                .filter(su -> su.getUpgrade().getUpgradeType() == WEAPON_TEAM)
                .count();
        if (weaponTeams > 1) {
            throw new WeaponTeamLimitExceededException("Unit can Take only One weapon team!!");
        }
    }


    public void validateBattleStandards(ArmyUnit armyUnit) {
        Army army = armyUnit.getArmy();
        List<ArmyUnit> armyUnits = army.getArmyUnitsList().stream()
                .filter(au -> au.getUnit().getUnitType() == UnitType.LORDS || au.getUnit().getUnitType() == UnitType.HERO)
                .toList();
        long bsbInArmy = armyUnits.stream()
                .filter(au ->
                        au.getSelectedUpgradesList().stream()
                                .anyMatch(su -> su.getUpgrade().getUpgradeType() == UpgradeType.BSB)
                )
                .count();
        if (bsbInArmy > 1) {
            throw new BsbLimitExceededException("Army can take only 1 BSB!");
        }
    }

    public boolean validateMagicBannerAndCheckPresence(ArmyUnit armyUnit) {
        long magicBannersAmount = armyUnit.getSelectedUpgradesList().stream()
                .filter(su -> su.getUpgrade().getUpgradeType() == UpgradeType.MAGIC_BANNER)
                .count();
        if (magicBannersAmount > 1) {
            throw new MagicBannerLimitExceededException("Unit can take only 1 Magic Banner!");
        }
        return magicBannersAmount == 1;
    }

    public void validateMagicBannerRestrictions(ArmyUnit armyUnit) {
        boolean haveMagicItems = armyUnit.getSelectedUpgradesList().stream()
                .anyMatch(su -> su.getUpgrade().getUpgradeType() == UpgradeType.MAGIC_ARMOUR
                        || su.getUpgrade().getUpgradeType() == UpgradeType.MAGIC_WEAPON || su.getUpgrade().getUpgradeType() == UpgradeType.MAGIC_ITEM);
        if (haveMagicItems) {
            throw new MagicBannerConflictException("Unit can't take Magic items with magic banner");
        }
    }


    public void checkLordsAndHeroUpgrades(ArmyUnit armyUnit) {
        double pointsLimit = switch (armyUnit.getUnit().getUnitType()) {
            case LORDS -> 100;
            case HERO -> 50;
            default -> 0;
        };
        if (armyUnit.getUnit().getUnitType() == UnitType.LORDS || armyUnit.getUnit().getUnitType() == UnitType.HERO) {
            List<SelectedUpgrade> selectedUpgrades = armyUnit.getSelectedUpgradesList();
            double upgradePoints = selectedUpgrades.stream()
                    .filter(su -> su.getUpgrade().getUpgradeType() == UpgradeType.MAGIC_WEAPON
                            || su.getUpgrade().getUpgradeType() == UpgradeType.MAGIC_ARMOUR
                            || su.getUpgrade().getUpgradeType() == UpgradeType.MAGIC_ITEM
                    )
                    .mapToDouble(su -> su.getUpgrade().getPointsCost())
                    .sum();
            if (upgradePoints > pointsLimit) {
                throw new LordsUpgradePointsExceededException(armyUnit.getUnit().getUnitType().toString() + " can but upgrades only for " + pointsLimit + " points!");
            }
        }
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

    public void checkAllUpgrades(ArmyUnit armyUnit) {
        List<SelectedUpgrade> selectedUpgrades = armyUnit.getSelectedUpgradesList();
        for (SelectedUpgrade su : selectedUpgrades) {
            switch (su.getUpgrade().getUpgradeType()) {
                case UNIT_EQUIPMENT:
                    su.setQuantity(armyUnit.getQuantity());
                    break;
                default:
                    su.setQuantity(1);
            }
        }




    }

}


