package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.army.model.*;
import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedUpgradeValidatorService;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unit.UnitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.oldworld.rosterbuilder.unit.UnitType.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ArmyPointsService {

    private final CurrentUserService currentUserService;
    private final SelectedUpgradeValidatorService selectedUpgradeValidatorService;

    ArmyPointsView calculateArmyPoints(Long armyId) {
        return calculateArmyPoints(currentUserService.getArmyOwnedByCurrentUser(armyId));
    }

    public ArmyPointsView calculateArmyPoints(Army army) {
        var pointsLimit = army.getPointsLimit();
        var used = usedPointsByType(army);
        var totalSpendPoints = used.values().stream().mapToDouble(Double::doubleValue).sum();

        var charactersLimit = pointsLimit * CHARACTERS_POINTS_SHARE;
        var charactersValid = used.get(LORDS) + used.get(HERO) <= charactersLimit;

        var core = new CorePointsView(used.get(CORE), CORE.pointsLimit(pointsLimit), CORE.isWithinLimit(used.get(CORE), pointsLimit));
        var special = new SpecialPointsView(used.get(SPECIAL), SPECIAL.pointsLimit(pointsLimit), SPECIAL.isWithinLimit(used.get(SPECIAL), pointsLimit));
        var rare = new RarePointsView(used.get(RARE), RARE.pointsLimit(pointsLimit), RARE.isWithinLimit(used.get(RARE), pointsLimit));
        var violations = new ArrayList<>(selectedUpgradeValidatorService.findArmyViolations(army));
        violations.addAll(findDuplicateViolations(army));

        var valid = totalSpendPoints <= pointsLimit
                && charactersValid
                && core.valid()
                && special.valid()
                && rare.valid()
                && violations.isEmpty();

        return new ArmyPointsView(
                totalSpendPoints,
                pointsLimit,
                new LordsPointsView(used.get(LORDS), charactersLimit, charactersValid),
                new HeroesPointsView(used.get(HERO), charactersLimit, charactersValid),
                core,
                special,
                rare,
                violations,
                valid);
    }

    List<String> findDuplicateViolations(Army army) {
        var violations = new ArrayList<String>();
        army.getArmyUnitsList().stream()
                .filter(armyUnit -> !armyUnit.isDetachment())
                .map(ArmyUnit::getUnit)
                .filter(unit -> unit.getUnitType().hasDuplicateLimit())
                .collect(groupingBy(Unit::getName, LinkedHashMap::new, toList()))
                .forEach((name, units) -> {
                    var unitType = units.getFirst().getUnitType();
                    var limit = unitType.duplicateLimit(army.getPointsLimit());
                    if (units.size() > limit) {
                        violations.add(name + ": " + unitType + " units can be taken at most " + limit + " times");
                    }
                });
        return violations;
    }

    public Map<UnitType, Double> usedPointsByType(Army army) {
        var used = new EnumMap<UnitType, Double>(UnitType.class);
        for (var unitType : UnitType.values()) {
            used.put(unitType, 0.0);
        }
        for (var armyUnit : army.getArmyUnitsList()) {
            used.merge(armyUnit.getUnit().getUnitType(), armyUnit.getTotalCost(), Double::sum);
        }
        return used;
    }
}
