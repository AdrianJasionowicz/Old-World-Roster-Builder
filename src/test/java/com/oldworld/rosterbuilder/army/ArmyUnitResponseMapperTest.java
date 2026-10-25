package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unit.UnitProfile;
import com.oldworld.rosterbuilder.unit.UnitType;
import com.oldworld.rosterbuilder.unitStats.StatsCalculatorService;
import com.oldworld.rosterbuilder.unitStats.model.ChampionDetails;
import com.oldworld.rosterbuilder.unitStats.model.UnitDetails;
import com.oldworld.rosterbuilder.upgrade.UpgradeProfile;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import com.oldworld.rosterbuilder.upgrade.model.ProfileView;
import com.oldworld.rosterbuilder.upgrade.model.UpgradeView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.oldworld.rosterbuilder.support.UpgradeFixtures.selected;
import static com.oldworld.rosterbuilder.support.UpgradeFixtures.unitUpgrade;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArmyUnitResponseMapperTest {

    @Mock
    StatsCalculatorService statsCalculatorService;
    @InjectMocks
    ArmyUnitResponseMapper armyUnitResponseMapper;

    private static final UnitDetails STATS = new UnitDetails(4, 3, 3, 3, 3, 1, 3, 1, 7, 5, null, List.of());

    private static ArmyUnit armyUnit(UnitType unitType, boolean canHaveDetachments) {
        var unit = new Unit();
        unit.setName("Halberdiers");
        unit.setUnitType(unitType);
        unit.setCanHaveDetachments(canHaveDetachments);
        var armyUnit = new ArmyUnit();
        armyUnit.setId(5L);
        armyUnit.setUnit(unit);
        armyUnit.setQuantity(20);
        armyUnit.setTotalCost(120);
        return armyUnit;
    }

    private void stats(ArmyUnit armyUnit, ChampionDetails champion) {
        when(statsCalculatorService.calculate(armyUnit)).thenReturn(STATS);
        when(statsCalculatorService.calculateChampion(armyUnit)).thenReturn(Optional.ofNullable(champion));
    }

    @Test
    @DisplayName("makeView maps a regiment with its stats and upgrades")
    void makeView() {
        var armyUnit = armyUnit(UnitType.CORE, true);
        var shield = selected(unitUpgrade(3L, "Shield", UpgradeType.UNIT_EQUIPMENT, 1));
        shield.setQuantity(20);
        armyUnit.getSelectedUpgradesList().add(shield);
        stats(armyUnit, null);

        var view = armyUnitResponseMapper.makeView(armyUnit);

        assertEquals(5L, view.id());
        assertEquals("Halberdiers", view.unitName());
        assertEquals(20, view.quantity());
        assertEquals(120, view.totalCost());
        assertEquals(4, view.m());
        assertEquals(5, view.basicSave());
        assertNull(view.wardSave());
        assertEquals(List.of(new UpgradeView(null, "Shield", 1, UpgradeType.UNIT_EQUIPMENT, "Shield")), view.selectedUpgrades());
        assertNull(view.parentId());
        assertTrue(view.canHaveDetachments());
        assertNull(view.champion());
        assertTrue(view.profiles().isEmpty());
        assertTrue(view.resizable());
    }

    @Test
    @DisplayName("makeView of a fixed size unit is not resizable")
    void makeViewFixedSize() {
        var armyUnit = armyUnit(UnitType.SPECIAL, false);
        armyUnit.getUnit().setFixedSize(true);
        stats(armyUnit, null);

        assertFalse(armyUnitResponseMapper.makeView(armyUnit).resizable());
    }

    @Test
    @DisplayName("makeView of a detachment points to its regiment and cannot have detachments")
    void makeViewDetachment() {
        var regiment = armyUnit(UnitType.CORE, true);
        var detachment = armyUnit(UnitType.CORE, true);
        detachment.setParent(regiment);
        stats(detachment, null);

        var view = armyUnitResponseMapper.makeView(detachment);

        assertEquals(5L, view.parentId());
        assertFalse(view.canHaveDetachments());
    }

    @Test
    @DisplayName("makeView adds the champion and the profiles of mounts and weapon teams")
    void makeViewWithChampionAndProfiles() {
        var armyUnit = armyUnit(UnitType.HERO, false);
        var horse = selected(unitUpgrade(4L, "Warhorse", UpgradeType.MOUNT, 12));
        var profile = new UpgradeProfile();
        profile.setName("Warhorse");
        profile.setM("8");
        profile.setBasicSave(null);
        horse.getUpgrade().getProfiles().add(profile);
        armyUnit.getSelectedUpgradesList().add(horse);
        var champion = new ChampionDetails("Sergeant", STATS);
        stats(armyUnit, champion);

        var view = armyUnitResponseMapper.makeView(armyUnit);

        assertEquals(champion, view.champion());
        assertEquals(List.of(new ProfileView("Warhorse", "8", null, null, null, null, null, null, null, null, null, null)), view.profiles());
        assertEquals(50, view.magicItemsPointsLimit());
        assertFalse(view.resizable());
    }

    @Test
    @DisplayName("makeView shows the crew of a war machine")
    void makeViewWithCrew() {
        var armyUnit = armyUnit(UnitType.SPECIAL, false);
        var crew = new UnitProfile();
        crew.setName("Crew (3)");
        crew.setWs("3");
        armyUnit.getUnit().getProfiles().add(crew);
        stats(armyUnit, null);

        assertEquals(List.of(new ProfileView("Crew (3)", null, "3", null, null, null, null, null, null, null, null, null)),
                armyUnitResponseMapper.makeView(armyUnit).profiles());
    }

    @Test
    @DisplayName("makeView passes a stats error on")
    void makeViewStatsError() {
        var armyUnit = armyUnit(UnitType.CORE, false);
        when(statsCalculatorService.calculate(armyUnit)).thenThrow(new IllegalStateException("broken stats"));

        assertThrows(IllegalStateException.class, () -> armyUnitResponseMapper.makeView(armyUnit));
    }
}
