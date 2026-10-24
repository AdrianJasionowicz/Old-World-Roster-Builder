package com.oldworld.rosterbuilder.selectedUpgrade;

import com.oldworld.rosterbuilder.army.Army;
import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.exception.BusinessRuleException;
import com.oldworld.rosterbuilder.unit.UnitType;
import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import com.oldworld.rosterbuilder.upgrade.RuneKind;
import com.oldworld.rosterbuilder.upgrade.Upgrade;
import com.oldworld.rosterbuilder.upgrade.UpgradeGroup;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class SelectedUpgradeValidatorService {

    private static final int MAX_WEAPON_TEAMS = 1;
    private static final int MAX_BATTLE_STANDARD_BEARERS = 1;
    private static final int MAX_RUNES_PER_ITEM = 3;
    private static final int MAX_MASTER_RUNES_PER_ITEM = 1;
    private static final String STANDARD_BEARER = "Standard Bearer";

    void validateSelection(ArmyUnit armyUnit, UnitUpgrade candidate) {
        findSelectionViolation(armyUnit, candidate).ifPresent(violation -> {
            throw new BusinessRuleException(violation);
        });
    }

    Optional<String> findSelectionViolation(ArmyUnit armyUnit, UnitUpgrade candidate) {
        var upgradesAfterSelection = new ArrayList<>(armyUnit.getSelectedUpgradesList());
        upgradesAfterSelection.add(new SelectedUpgrade(armyUnit, candidate));

        var violations = new ArrayList<>(findUnitViolations(armyUnit, upgradesAfterSelection));
        var upgrade = candidate.getUpgrade();
        if (upgrade.getUpgradeType() == UpgradeType.BSB && armyUnit.getUnit().getUnitType().isCharacter()) {
            var otherBattleStandardBearers = countBattleStandardBearers(armyUnit.getArmy(), armyUnit);
            if (otherBattleStandardBearers + 1 > MAX_BATTLE_STANDARD_BEARERS) {
                violations.add("Army can take only " + MAX_BATTLE_STANDARD_BEARERS + " Battle Standard Bearer");
            }
        }
        if (upgrade.isUniqueInArmy() && isTakenByAnotherUnit(armyUnit, upgrade)) {
            violations.add(upgrade.getName() + " is already taken by another unit");
        }
        return violations.stream().findFirst();
    }

    public List<String> findArmyViolations(Army army) {
        var violations = new ArrayList<String>();
        for (var armyUnit : army.getArmyUnitsList()) {
            findUnitViolations(armyUnit, armyUnit.getSelectedUpgradesList()).stream()
                    .map(violation -> armyUnit.getUnit().getName() + ": " + violation)
                    .forEach(violations::add);
        }
        if (countBattleStandardBearers(army, null) > MAX_BATTLE_STANDARD_BEARERS) {
            violations.add("Army can take only " + MAX_BATTLE_STANDARD_BEARERS + " Battle Standard Bearer");
        }
        army.getArmyUnitsList().stream()
                .flatMap(armyUnit -> armyUnit.getSelectedUpgradesList().stream())
                .map(SelectedUpgrade::getUpgrade)
                .filter(Upgrade::isUniqueInArmy)
                .collect(groupingBy(Upgrade::getName, LinkedHashMap::new, counting()))
                .forEach((name, taken) -> {
                    if (taken > 1) {
                        violations.add(name + " can be taken only once per army");
                    }
                });
        return violations;
    }

    List<String> findUnitViolations(ArmyUnit armyUnit, List<SelectedUpgrade> upgrades) {
        var violations = new ArrayList<String>();
        if (count(upgrades, UpgradeType.WEAPON_TEAM) > MAX_WEAPON_TEAMS) {
            violations.add("Unit can take only " + MAX_WEAPON_TEAMS + " weapon team");
        }
        violations.addAll(findItemSlotViolations(upgrades));
        violations.addAll(findMagicBannerViolations(armyUnit, upgrades));
        violations.addAll(findMagicItemsPointsViolations(armyUnit, upgrades));
        violations.addAll(findGroupViolations(armyUnit, upgrades));
        return violations;
    }

    private List<String> findItemSlotViolations(List<SelectedUpgrade> upgrades) {
        var violations = new ArrayList<String>();
        for (var slot : UpgradeType.values()) {
            if (!slot.isSingleItemSlot()) {
                continue;
            }
            var inSlot = upgrades.stream().filter(su -> su.getUpgradeType() == slot).toList();
            var runes = inSlot.stream().filter(su -> su.getUpgrade().getRune() != null).toList();
            var items = inSlot.size() - runes.size() + (runes.isEmpty() ? 0 : 1);
            if (items > 1) {
                violations.add("Unit can take only one " + slot.displayName());
            }
            if (runes.size() > MAX_RUNES_PER_ITEM) {
                violations.add("A runic " + slot.displayName() + " can carry only " + MAX_RUNES_PER_ITEM + " runes");
            }
            var masterRunes = runes.stream().filter(su -> su.getUpgrade().getRune() == RuneKind.MASTER_RUNE).count();
            if (masterRunes > MAX_MASTER_RUNES_PER_ITEM) {
                violations.add("A runic " + slot.displayName() + " can carry only " + MAX_MASTER_RUNES_PER_ITEM + " master rune");
            }
        }
        return violations;
    }

    private List<String> findMagicBannerViolations(ArmyUnit armyUnit, List<SelectedUpgrade> upgrades) {
        var banners = upgrades.stream().filter(su -> su.getUpgradeType() == UpgradeType.MAGIC_BANNER).toList();
        if (banners.isEmpty()) {
            return List.of();
        }
        var violations = new ArrayList<String>();
        if (upgrades.stream().anyMatch(su -> su.getUpgradeType().isMagicItem())) {
            violations.add("Unit can't take magic items together with a magic banner");
        }
        findMissingBannerCarrier(armyUnit, upgrades).ifPresent(violations::add);
        var bannerPointsLimit = armyUnit.getUnit().getMagicBannerPointsLimit();
        if (bannerPointsLimit != null && sumPoints(banners) > bannerPointsLimit) {
            violations.add("Unit can take magic banners worth up to " + points(bannerPointsLimit) + " points");
        }
        return violations;
    }

    private Optional<String> findMissingBannerCarrier(ArmyUnit armyUnit, List<SelectedUpgrade> upgrades) {
        var unit = armyUnit.getUnit();
        if (unit.getUnitType().isCharacter()) {
            return count(upgrades, UpgradeType.BSB) > 0
                    ? Optional.empty()
                    : Optional.of("Only a Battle Standard Bearer can carry a magic banner");
        }
        var offersStandardBearer = unit.getUnitUpgrades().stream().anyMatch(this::isStandardBearer);
        var hasStandardBearer = upgrades.stream().anyMatch(su -> isStandardBearer(su.getUnitUpgrade()));
        return offersStandardBearer && !hasStandardBearer
                ? Optional.of("Unit needs a Standard Bearer to carry a magic banner")
                : Optional.empty();
    }

    private List<String> findMagicItemsPointsViolations(ArmyUnit armyUnit, List<SelectedUpgrade> upgrades) {
        var unit = armyUnit.getUnit();
        if (!unit.getUnitType().isCharacter()) {
            return List.of();
        }
        var magicItems = upgrades.stream().filter(su -> su.getUpgradeType().isMagicItem()).toList();
        var allowance = unit.magicItemsAllowance();
        return sumPoints(magicItems) > allowance
                ? List.of(unit.getUnitType() + " can spend only " + points(allowance) + " points on magic items")
                : List.of();
    }

    private List<String> findGroupViolations(ArmyUnit armyUnit, List<SelectedUpgrade> upgrades) {
        var violations = new ArrayList<String>();
        var unitType = armyUnit.getUnit().getUnitType();
        upgrades.stream()
                .filter(su -> su.getUpgrade().getUpgradeGroup() != null)
                .collect(groupingBy(su -> su.getUpgrade().getUpgradeGroup().getName(), LinkedHashMap::new, toList()))
                .forEach((name, selected) -> {
                    var group = selected.getFirst().getUpgrade().getUpgradeGroup();
                    var groupName = name.toLowerCase();
                    if (group.getMaxChoices() != null && selected.size() > group.getMaxChoices()) {
                        violations.add("Unit can take only " + group.getMaxChoices() + " " + groupName);
                    }
                    var pointsLimit = pointsLimit(group, unitType);
                    if (pointsLimit != null && sumPoints(selected) > pointsLimit) {
                        violations.add(unitType + " can spend only " + points(pointsLimit) + " points on " + groupName + "s");
                    }
                });
        return violations;
    }

    private Double pointsLimit(UpgradeGroup group, UnitType unitType) {
        return switch (unitType) {
            case LORDS -> group.getLordPointsLimit();
            case HERO -> group.getHeroPointsLimit();
            default -> null;
        };
    }

    private boolean isStandardBearer(UnitUpgrade unitUpgrade) {
        var upgrade = unitUpgrade.getUpgrade();
        return upgrade.getUpgradeType() == UpgradeType.COMMAND_GROUP && STANDARD_BEARER.equals(upgrade.getName());
    }

    private boolean isTakenByAnotherUnit(ArmyUnit armyUnit, Upgrade upgrade) {
        return armyUnit.getArmy().getArmyUnitsList().stream()
                .filter(other -> other != armyUnit)
                .flatMap(other -> other.getSelectedUpgradesList().stream())
                .anyMatch(su -> Objects.equals(su.getUpgrade().getId(), upgrade.getId()));
    }

    private double sumPoints(List<SelectedUpgrade> upgrades) {
        return upgrades.stream().mapToDouble(SelectedUpgrade::getPointsCost).sum();
    }

    private String points(double value) {
        return value == Math.rint(value) ? String.valueOf((long) value) : String.valueOf(value);
    }

    private long count(List<SelectedUpgrade> upgrades, UpgradeType upgradeType) {
        return upgrades.stream()
                .filter(su -> su.getUpgradeType() == upgradeType)
                .count();
    }

    private long countBattleStandardBearers(Army army, ArmyUnit excluded) {
        return army.getArmyUnitsList().stream()
                .filter(armyUnit -> armyUnit != excluded)
                .filter(armyUnit -> armyUnit.getUnit().getUnitType().isCharacter())
                .filter(armyUnit -> count(armyUnit.getSelectedUpgradesList(), UpgradeType.BSB) > 0)
                .count();
    }
}
