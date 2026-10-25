package com.oldworld.rosterbuilder.unit;

import com.oldworld.rosterbuilder.specialRule.SpecialRuleRequest;
import com.oldworld.rosterbuilder.unit.model.UnitRequest;
import com.oldworld.rosterbuilder.unit.model.UnitResponse;
import com.oldworld.rosterbuilder.unitStats.model.UnitStatsRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitMapperTest {

    private final UnitMapper unitMapper = new UnitMapper();

    private static UnitRequest request(List<SpecialRuleRequest> specialRules, UnitStatsRequest stats) {
        return new UnitRequest("Clanrat", 4.0, 20, UnitType.CORE, UnitFaction.SKAVEN, specialRules, stats, List.of(), false);
    }

    private static UnitStatsRequest stats() {
        return new UnitStatsRequest(5, 3, 3, 3, 3, 1, 4, 1, 5, 6, null);
    }

    @Test
    @DisplayName("mapUnitRqToUnit copies the unit data and its stats")
    void mapUnitRqToUnit() {
        var unit = unitMapper.mapUnitRqToUnit(request(List.of(), stats()));

        assertEquals("Clanrat", unit.getName());
        assertEquals(4.0, unit.getPointsCostPerUnit());
        assertEquals(20, unit.getMinQuantity());
        assertEquals(UnitType.CORE, unit.getUnitType());
        assertEquals(UnitFaction.SKAVEN, unit.getUnitFaction());
        assertEquals(5, unit.getUnitStats().getM());
        assertEquals(4, unit.getUnitStats().getI());
        assertEquals(6, unit.getUnitStats().getBasicSave());
        assertNull(unit.getUnitStats().getWardSave());
        assertTrue(unit.getUnitUpgrades().isEmpty());
        assertFalse(unit.isFixedSize());
    }

    @Test
    @DisplayName("mapUnitRqToUnit keeps the fixed size of a war machine")
    void mapUnitRqToUnitFixedSize() {
        var request = new UnitRequest("Doomwheel", 150, 1, UnitType.RARE, UnitFaction.SKAVEN, List.of(), stats(), List.of(), true);

        assertTrue(unitMapper.mapUnitRqToUnit(request).isFixedSize());
    }

    @Test
    @DisplayName("mapUnitRqToUnit turns special rules into new entities")
    void mapUnitRqToUnitWithSpecialRules() {
        var unit = unitMapper.mapUnitRqToUnit(request(List.of(new SpecialRuleRequest("Strength in Numbers", "Ld bonus")), stats()));

        assertEquals(1, unit.getSpecialRulesList().size());
        assertNull(unit.getSpecialRulesList().getFirst().getId());
        assertEquals("Strength in Numbers", unit.getSpecialRulesList().getFirst().getName());
        assertEquals("Ld bonus", unit.getSpecialRulesList().getFirst().getDescription());
    }

    @Test
    @DisplayName("mapUnitRqToUnit without special rules gives an empty, changeable list")
    void mapUnitRqToUnitWithoutSpecialRules() {
        var unit = unitMapper.mapUnitRqToUnit(request(null, stats()));

        assertTrue(unit.getSpecialRulesList().isEmpty());
        assertDoesNotThrow(() -> unit.getSpecialRulesList().add(null));
    }

    @Test
    @DisplayName("mapUnitRqToUnit without stats throws NullPointerException")
    void mapUnitRqToUnitWithoutStats() {
        assertThrows(NullPointerException.class, () -> unitMapper.mapUnitRqToUnit(request(List.of(), null)));
    }

    @Test
    @DisplayName("mapUnitToUnitResponse counts the cost of the minimum unit size")
    void mapUnitToUnitResponse() {
        var unit = unitMapper.mapUnitRqToUnit(request(List.of(), stats()));
        unit.setId(7L);
        unit.setCanBeDetachment(true);

        assertEquals(new UnitResponse(7L, "Clanrat", 4.0, 80.0, UnitType.CORE, true), unitMapper.mapUnitToUnitResponse(unit));
    }

    @Test
    @DisplayName("mapUnitToUnitResponse of a free unit costs nothing")
    void mapUnitToUnitResponseFree() {
        var unit = new Unit();
        unit.setName("Slave");
        unit.setMinQuantity(20);

        assertEquals(0.0, unitMapper.mapUnitToUnitResponse(unit).totalCost());
    }

    @Test
    @DisplayName("mapUnitToUnitResponse without a minimum size throws NullPointerException")
    void mapUnitToUnitResponseWithoutMinQuantity() {
        var unit = new Unit();
        unit.setName("Broken");

        assertThrows(NullPointerException.class, () -> unitMapper.mapUnitToUnitResponse(unit));
    }
}
