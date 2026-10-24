package com.oldworld.rosterbuilder.selectedUpgrade;

import com.oldworld.rosterbuilder.army.Army;
import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.exception.BusinessRuleException;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unit.UnitType;
import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import com.oldworld.rosterbuilder.upgrade.RuneKind;
import com.oldworld.rosterbuilder.upgrade.UpgradeGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.oldworld.rosterbuilder.support.UpgradeFixtures.selected;
import static com.oldworld.rosterbuilder.support.UpgradeFixtures.unitUpgrade;
import static com.oldworld.rosterbuilder.upgrade.UpgradeType.*;
import static org.junit.jupiter.api.Assertions.*;

class SelectedUpgradeValidatorServiceTest {

    private final SelectedUpgradeValidatorService validator = new SelectedUpgradeValidatorService();

    private ArmyUnit armyUnit(Army army, UnitType unitType, SelectedUpgrade... selectedUpgrades) {
        var unit = new Unit();
        unit.setName(unitType + " unit");
        unit.setUnitType(unitType);
        var armyUnit = new ArmyUnit();
        armyUnit.setUnit(unit);
        armyUnit.setArmy(army);
        armyUnit.setSelectedUpgradesList(new ArrayList<>(List.of(selectedUpgrades)));
        army.getArmyUnitsList().add(armyUnit);
        return armyUnit;
    }

    @Test
    @DisplayName("Second weapon team is rejected")
    void secondWeaponTeam() {
        var armyUnit = armyUnit(new Army(), UnitType.CORE, selected(1L, WEAPON_TEAM));

        assertThrows(BusinessRuleException.class,
                () -> validator.validateSelection(armyUnit, unitUpgrade(2L, "Ratling Gun", WEAPON_TEAM, 55)));
    }

    @Test
    @DisplayName("findSelectionViolation explains why an option cannot be selected")
    void findSelectionViolationGivesReason() {
        var armyUnit = armyUnit(new Army(), UnitType.CORE, selected(1L, WEAPON_TEAM));

        var violation = validator.findSelectionViolation(armyUnit, unitUpgrade(2L, "Ratling Gun", WEAPON_TEAM, 55));

        assertEquals("Unit can take only 1 weapon team", violation.orElseThrow());
        assertTrue(validator.findSelectionViolation(armyUnit, unitUpgrade(3L, "Shield", UNIT_EQUIPMENT, 1)).isEmpty());
    }

    @Test
    @DisplayName("First weapon team is fine")
    void firstWeaponTeam() {
        var armyUnit = armyUnit(new Army(), UnitType.CORE);

        assertDoesNotThrow(() -> validator.validateSelection(armyUnit, unitUpgrade(2L, "Ratling Gun", WEAPON_TEAM, 55)));
    }

    @Test
    @DisplayName("Second BSB in the army is rejected")
    void secondBattleStandardBearer() {
        var army = new Army();
        armyUnit(army, UnitType.LORDS, selected(1L, BSB));
        var hero = armyUnit(army, UnitType.HERO);

        assertThrows(BusinessRuleException.class, () -> validator.validateSelection(hero, unitUpgrade(2L, "BSB", BSB, 25)));
    }

    @Test
    @DisplayName("Magic banner and magic items cannot be combined")
    void magicBannerWithMagicItem() {
        var armyUnit = armyUnit(new Army(), UnitType.HERO, selected(1L, MAGIC_BANNER));

        assertThrows(BusinessRuleException.class,
                () -> validator.validateSelection(armyUnit, unitUpgrade(2L, "Sword", MAGIC_WEAPON, 10)));
    }

    @Test
    @DisplayName("Hero may spend 50 points on magic items")
    void heroMagicItemsLimit() {
        var armyUnit = armyUnit(new Army(), UnitType.HERO, selected(1L, MAGIC_ITEM, 49));

        assertThrows(BusinessRuleException.class,
                () -> validator.validateSelection(armyUnit, unitUpgrade(2L, "Talisman", MAGIC_ITEM, 2)));
    }

    @Test
    @DisplayName("Lord may spend 100 points on magic items")
    void lordMagicItemsLimit() {
        var armyUnit = armyUnit(new Army(), UnitType.LORDS, selected(1L, MAGIC_WEAPON, 60));

        assertDoesNotThrow(() -> validator.validateSelection(armyUnit, unitUpgrade(2L, "Armour", MAGIC_ARMOUR, 40)));
    }

    @Test
    @DisplayName("Non-magic upgrades do not count towards the magic items limit")
    void mountDoesNotCount() {
        var armyUnit = armyUnit(new Army(), UnitType.HERO);

        assertDoesNotThrow(() -> validator.validateSelection(armyUnit, unitUpgrade(2L, "Great Pox Rat", MOUNT, 200)));
    }

    @Test
    @DisplayName("Character can take only one item of each magic category")
    void oneMagicWeapon() {
        var armyUnit = armyUnit(new Army(), UnitType.LORDS, selected(1L, MAGIC_WEAPON, 20));

        var violation = validator.findSelectionViolation(armyUnit, unitUpgrade(2L, "Sword", MAGIC_WEAPON, 20));

        assertEquals("Unit can take only one magic weapon", violation.orElseThrow());
        assertTrue(validator.findSelectionViolation(armyUnit, unitUpgrade(3L, "Talisman", TALISMAN, 20)).isEmpty());
    }

    @Test
    @DisplayName("Runes on one item count as a single item, up to 3 runes and 1 master rune")
    void runicWeapon() {
        var armyUnit = armyUnit(new Army(), UnitType.LORDS,
                selected(rune(1L, RuneKind.MASTER_RUNE)), selected(rune(2L, RuneKind.RUNE)));

        assertTrue(validator.findSelectionViolation(armyUnit, rune(3L, RuneKind.RUNE)).isEmpty());
        assertEquals("A runic magic weapon can carry only 1 master rune",
                validator.findSelectionViolation(armyUnit, rune(4L, RuneKind.MASTER_RUNE)).orElseThrow());

        armyUnit.getSelectedUpgradesList().add(selected(rune(5L, RuneKind.RUNE)));
        assertEquals("A runic magic weapon can carry only 3 runes",
                validator.findSelectionViolation(armyUnit, rune(6L, RuneKind.RUNE)).orElseThrow());
    }

    @Test
    @DisplayName("Unit limit on magic items points overrides the unit type default")
    void dwarfLordAllowance() {
        var armyUnit = armyUnit(new Army(), UnitType.LORDS, selected(1L, MAGIC_WEAPON, 100));
        armyUnit.getUnit().setMagicItemsPointsLimit(125.0);

        assertTrue(validator.findSelectionViolation(armyUnit, unitUpgrade(2L, "Armour", MAGIC_ARMOUR, 25)).isEmpty());
        assertEquals("LORDS can spend only 125 points on magic items",
                validator.findSelectionViolation(armyUnit, unitUpgrade(3L, "Armour", MAGIC_ARMOUR, 26)).orElseThrow());
    }

    @Test
    @DisplayName("Only one upgrade of a group like mounts can be taken")
    void oneMount() {
        var mounts = new UpgradeGroup("Mount", 1, null, null);
        var armyUnit = armyUnit(new Army(), UnitType.LORDS, selected(inGroup(unitUpgrade(1L, "Steed", MOUNT, 20), mounts)));

        var violation = validator.findSelectionViolation(armyUnit, inGroup(unitUpgrade(2L, "Dragon", MOUNT, 300), mounts));

        assertEquals("Unit can take only 1 mount", violation.orElseThrow());
    }

    @Test
    @DisplayName("Group points limit depends on the character type")
    void vampiricPowersLimit() {
        var powers = new UpgradeGroup("Vampiric Power", null, 100.0, 50.0);
        var vampire = armyUnit(new Army(), UnitType.HERO, selected(inGroup(unitUpgrade(1L, "Red Fury", UNIT_EQUIPMENT, 35), powers)));

        var violation = validator.findSelectionViolation(vampire, inGroup(unitUpgrade(2L, "Quickblood", UNIT_EQUIPMENT, 20), powers));

        assertEquals("HERO can spend only 50 points on vampiric powers", violation.orElseThrow());
    }

    @Test
    @DisplayName("A unique magic item cannot be taken by two units of the army")
    void uniqueInArmy() {
        var army = new Army();
        var sword = unitUpgrade(1L, "Ogre Blade", MAGIC_WEAPON, 40);
        sword.getUpgrade().setUniqueInArmy(true);
        armyUnit(army, UnitType.LORDS, selected(sword));
        var hero = armyUnit(army, UnitType.HERO);

        assertEquals("Ogre Blade is already taken by another unit", validator.findSelectionViolation(hero, sword).orElseThrow());

        hero.getSelectedUpgradesList().add(selected(sword));
        assertTrue(validator.findArmyViolations(army).contains("Ogre Blade can be taken only once per army"));
    }

    @Test
    @DisplayName("Character needs a BSB and a unit its own limit to carry a magic banner")
    void magicBannerCarrier() {
        var hero = armyUnit(new Army(), UnitType.HERO);
        assertEquals("Only a Battle Standard Bearer can carry a magic banner",
                validator.findSelectionViolation(hero, unitUpgrade(1L, "War Banner", MAGIC_BANNER, 35)).orElseThrow());

        hero.getSelectedUpgradesList().add(selected(2L, BSB));
        assertTrue(validator.findSelectionViolation(hero, unitUpgrade(1L, "War Banner", MAGIC_BANNER, 35)).isEmpty());

        var core = armyUnit(new Army(), UnitType.CORE);
        core.getUnit().setMagicBannerPointsLimit(25.0);
        assertEquals("Unit can take magic banners worth up to 25 points",
                validator.findSelectionViolation(core, unitUpgrade(3L, "War Banner", MAGIC_BANNER, 35)).orElseThrow());
    }

    private static UnitUpgrade rune(long id, RuneKind runeKind) {
        var rune = unitUpgrade(id, "Rune " + id, MAGIC_WEAPON, 5);
        rune.getUpgrade().setRune(runeKind);
        return rune;
    }

    private static UnitUpgrade inGroup(UnitUpgrade unitUpgrade, UpgradeGroup group) {
        unitUpgrade.getUpgrade().setUpgradeGroup(group);
        return unitUpgrade;
    }

    @Test
    @DisplayName("findArmyViolations reports every broken rule with the unit name")
    void findArmyViolations() {
        var army = new Army();
        armyUnit(army, UnitType.CORE, selected(1L, WEAPON_TEAM), selected(2L, WEAPON_TEAM));
        armyUnit(army, UnitType.LORDS, selected(3L, BSB));
        armyUnit(army, UnitType.HERO, selected(4L, BSB));

        var violations = validator.findArmyViolations(army);

        assertEquals(2, violations.size());
        assertTrue(violations.getFirst().startsWith("CORE unit: "));
    }

    @Test
    @DisplayName("findArmyViolations of a legal army is empty")
    void legalArmy() {
        var army = new Army();
        armyUnit(army, UnitType.CORE, selected(1L, WEAPON_TEAM));
        armyUnit(army, UnitType.LORDS, selected(2L, BSB));

        assertTrue(validator.findArmyViolations(army).isEmpty());
    }
}
