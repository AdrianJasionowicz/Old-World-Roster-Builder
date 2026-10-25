package com.oldworld.rosterbuilder.armyUnit;

import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unit.UnitProfile;
import com.oldworld.rosterbuilder.upgrade.StatProfile;
import com.oldworld.rosterbuilder.upgrade.UpgradeProfile;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.oldworld.rosterbuilder.support.UpgradeFixtures.selected;
import static com.oldworld.rosterbuilder.support.UpgradeFixtures.unitUpgrade;
import static org.junit.jupiter.api.Assertions.*;

class ArmyUnitTest {

    private static ArmyUnit armyUnit() {
        var armyUnit = new ArmyUnit();
        armyUnit.setUnit(new Unit());
        return armyUnit;
    }

    private static <T extends StatProfile> T named(T profile, String name) {
        profile.setName(name);
        return profile;
    }

    @Test
    @DisplayName("getProfiles lists the unit's own profiles before the profiles of its upgrades")
    void getProfiles() {
        var armyUnit = armyUnit();
        armyUnit.getUnit().getProfiles().add(named(new UnitProfile(), "Crew (3)"));
        var horse = selected(unitUpgrade(1L, "Warhorse", UpgradeType.MOUNT, 12));
        horse.getUpgrade().getProfiles().add(named(new UpgradeProfile(), "Warhorse"));
        armyUnit.getSelectedUpgradesList().add(horse);

        assertEquals(List.of("Crew (3)", "Warhorse"), armyUnit.getProfiles().stream().map(StatProfile::getName).toList());
    }

    @Test
    @DisplayName("getProfiles of a plain unit is empty")
    void getProfilesEmpty() {
        assertTrue(armyUnit().getProfiles().isEmpty());
    }

    @Test
    @DisplayName("getProfiles without a catalog unit throws NullPointerException")
    void getProfilesWithoutUnit() {
        assertThrows(NullPointerException.class, () -> new ArmyUnit().getProfiles());
    }
}
