package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.exception.ForbiddenException;
import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedUpgradeValidatorService;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unit.UnitType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.oldworld.rosterbuilder.unit.UnitType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArmyPointsServiceTest {

    @Mock
    CurrentUserService currentUserService;
    @Mock
    SelectedUpgradeValidatorService selectedUpgradeValidatorService;
    @InjectMocks
    ArmyPointsService armyPointsService;

    private static Army army(double pointsLimit) {
        var army = new Army();
        army.setId(1L);
        army.setPointsLimit(pointsLimit);
        return army;
    }

    private static ArmyUnit add(Army army, String name, UnitType unitType, double totalCost) {
        var unit = new Unit();
        unit.setName(name);
        unit.setUnitType(unitType);
        var armyUnit = new ArmyUnit();
        armyUnit.setUnit(unit);
        armyUnit.setArmy(army);
        armyUnit.setTotalCost(totalCost);
        army.getArmyUnitsList().add(armyUnit);
        return armyUnit;
    }

    private void noUpgradeViolations() {
        lenient().when(selectedUpgradeValidatorService.findArmyViolations(any())).thenReturn(List.of());
    }

    @Test
    @DisplayName("calculateArmyPoints: a legal army is valid with points per category")
    void calculateArmyPointsValid() {
        noUpgradeViolations();
        var army = army(2000);
        add(army, "Warlord", LORDS, 300);
        add(army, "Chieftain", HERO, 200);
        add(army, "Clanrat", CORE, 600);
        add(army, "Stormvermin", SPECIAL, 400);
        add(army, "Doomwheel", RARE, 150);

        var points = armyPointsService.calculateArmyPoints(army);

        assertTrue(points.valid());
        assertEquals(1650, points.usedPoints());
        assertEquals(2000, points.pointsLimit());
        assertEquals(1000, points.lordsPointsView().availableLords());
        assertEquals(500, points.corePointsView().minimalCore());
        assertEquals(500, points.rarePointsView().availableRare());
        assertTrue(points.violations().isEmpty());
    }

    @Test
    @DisplayName("calculateArmyPoints: characters over half of the army make it invalid")
    void calculateArmyPointsTooManyCharacters() {
        noUpgradeViolations();
        var army = army(2000);
        add(army, "Warlord", LORDS, 600);
        add(army, "Chieftain", HERO, 500);
        add(army, "Clanrat", CORE, 600);

        var points = armyPointsService.calculateArmyPoints(army);

        assertFalse(points.valid());
        assertFalse(points.lordsPointsView().valid());
        assertFalse(points.heroesPointsView().valid());
        assertTrue(points.corePointsView().valid());
    }

    @Test
    @DisplayName("calculateArmyPoints: too little core makes the army invalid")
    void calculateArmyPointsTooLittleCore() {
        noUpgradeViolations();
        var army = army(2000);
        add(army, "Clanrat", CORE, 499);

        var points = armyPointsService.calculateArmyPoints(army);

        assertFalse(points.valid());
        assertFalse(points.corePointsView().valid());
    }

    @Test
    @DisplayName("calculateArmyPoints: more points than the limit make the army invalid")
    void calculateArmyPointsOverLimit() {
        noUpgradeViolations();
        var army = army(1000);
        add(army, "Clanrat", CORE, 600);
        add(army, "Stormvermin", SPECIAL, 450);

        var points = armyPointsService.calculateArmyPoints(army);

        assertFalse(points.valid());
        assertTrue(points.specialPointsView().valid());
        assertEquals(1050, points.usedPoints());
    }

    @Test
    @DisplayName("calculateArmyPoints: upgrade rule violations are listed and make the army invalid")
    void calculateArmyPointsWithUpgradeViolations() {
        var army = army(2000);
        add(army, "Clanrat", CORE, 600);
        when(selectedUpgradeValidatorService.findArmyViolations(army)).thenReturn(List.of("Warlord: Unit can take only 1 mount"));

        var points = armyPointsService.calculateArmyPoints(army);

        assertFalse(points.valid());
        assertEquals(List.of("Warlord: Unit can take only 1 mount"), points.violations());
    }

    @Test
    @DisplayName("calculateArmyPoints: an empty army is invalid only because it has no core")
    void calculateArmyPointsEmptyArmy() {
        noUpgradeViolations();

        var points = armyPointsService.calculateArmyPoints(army(2000));

        assertEquals(0, points.usedPoints());
        assertFalse(points.corePointsView().valid());
        assertTrue(points.lordsPointsView().valid());
        assertFalse(points.valid());
    }

    @Test
    @DisplayName("calculateArmyPoints(id) calculates the points of the owned army")
    void calculateArmyPointsById() {
        noUpgradeViolations();
        var army = army(2000);
        add(army, "Clanrat", CORE, 600);
        when(currentUserService.getArmyOwnedByCurrentUser(1L)).thenReturn(army);

        assertEquals(600, armyPointsService.calculateArmyPoints(1L).usedPoints());
    }

    @Test
    @DisplayName("calculateArmyPoints(id) of a missing army throws NotFoundException")
    void calculateArmyPointsByIdNotFound() {
        when(currentUserService.getArmyOwnedByCurrentUser(1L)).thenThrow(new NotFoundException("Army with id 1 not found"));

        assertThrows(NotFoundException.class, () -> armyPointsService.calculateArmyPoints(1L));
    }

    @Test
    @DisplayName("calculateArmyPoints(id) of another user's army throws ForbiddenException")
    void calculateArmyPointsByIdForbidden() {
        when(currentUserService.getArmyOwnedByCurrentUser(1L)).thenThrow(new ForbiddenException("Access denied"));

        assertThrows(ForbiddenException.class, () -> armyPointsService.calculateArmyPoints(1L));
    }

    @Test
    @DisplayName("findDuplicateViolations: the 4th copy of a Special unit is a violation")
    void findDuplicateViolationsSpecial() {
        var army = army(2000);
        for (var i = 0; i < 4; i++) {
            add(army, "Stormvermin", SPECIAL, 100);
        }

        assertEquals(List.of("Stormvermin: SPECIAL units can be taken at most 3 times"), armyPointsService.findDuplicateViolations(army));
    }

    @Test
    @DisplayName("findDuplicateViolations: 3 Rare copies break the limit only below 3000 points")
    void findDuplicateViolationsRareLargeGame() {
        var smallArmy = army(2999);
        var largeArmy = army(3000);
        for (var i = 0; i < 3; i++) {
            add(smallArmy, "Doomwheel", RARE, 150);
            add(largeArmy, "Doomwheel", RARE, 150);
        }

        assertEquals(List.of("Doomwheel: RARE units can be taken at most 2 times"), armyPointsService.findDuplicateViolations(smallArmy));
        assertTrue(armyPointsService.findDuplicateViolations(largeArmy).isEmpty());
    }

    @Test
    @DisplayName("findDuplicateViolations: core units and detachments have no copy limit")
    void findDuplicateViolationsIgnoresCoreAndDetachments() {
        var army = army(2000);
        var regiment = add(army, "Clanrat", CORE, 100);
        for (var i = 0; i < 5; i++) {
            add(army, "Clanrat", CORE, 100);
        }
        for (var i = 0; i < 4; i++) {
            add(army, "Stormvermin", SPECIAL, 100).setParent(regiment);
        }

        assertTrue(armyPointsService.findDuplicateViolations(army).isEmpty());
    }

    @Test
    @DisplayName("usedPointsByType sums the points of each unit type")
    void usedPointsByType() {
        var army = army(2000);
        add(army, "Clanrat", CORE, 200);
        add(army, "Slave", CORE, 50.5);
        add(army, "Warlord", LORDS, 150);

        var used = armyPointsService.usedPointsByType(army);

        assertEquals(250.5, used.get(CORE));
        assertEquals(150, used.get(LORDS));
    }

    @Test
    @DisplayName("usedPointsByType has zero for every type of an empty army")
    void usedPointsByTypeEmptyArmy() {
        var used = armyPointsService.usedPointsByType(army(2000));

        assertEquals(UnitType.values().length, used.size());
        assertTrue(used.values().stream().allMatch(points -> points == 0.0));
    }

    @Test
    @DisplayName("usedPointsByType fails for a unit without a type")
    void usedPointsByTypeWithoutType() {
        var army = army(2000);
        add(army, "Broken", null, 100);

        assertThrows(NullPointerException.class, () -> armyPointsService.usedPointsByType(army));
    }
}
