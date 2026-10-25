package com.oldworld.rosterbuilder.unit;

import com.oldworld.rosterbuilder.specialRule.SpecialRule;
import com.oldworld.rosterbuilder.specialRule.SpecialRuleRequest;
import com.oldworld.rosterbuilder.specialRule.SpecialRuleService;
import com.oldworld.rosterbuilder.unit.model.UnitRequest;
import com.oldworld.rosterbuilder.unit.model.UnitResponse;
import com.oldworld.rosterbuilder.unitStats.model.UnitStatsRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitMapperTest {

    @Mock
    SpecialRuleService specialRuleService;
    @InjectMocks
    UnitMapper unitMapper;

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
    @DisplayName("mapUnitRqToUnit links special rules from the catalog")
    void mapUnitRqToUnitWithSpecialRules() {
        var fear = new SpecialRule(1L, "Fear", "Causes Fear.");
        when(specialRuleService.findOrCreate("Fear", "Causes Fear.")).thenReturn(fear);

        var unit = unitMapper.mapUnitRqToUnit(request(List.of(new SpecialRuleRequest("Fear", "Causes Fear.")), stats()));

        assertEquals(List.of(fear), unit.getSpecialRulesList());
    }

    @Test
    @DisplayName("mapUnitRqToUnit links a rule given twice only once")
    void mapUnitRqToUnitWithDuplicateSpecialRules() {
        var fear = new SpecialRule(1L, "Fear", "Causes Fear.");
        when(specialRuleService.findOrCreate("Fear", "Causes Fear.")).thenReturn(fear);
        var fearRequest = new SpecialRuleRequest("Fear", "Causes Fear.");

        var unit = unitMapper.mapUnitRqToUnit(request(List.of(fearRequest, fearRequest), stats()));

        assertEquals(List.of(fear), unit.getSpecialRulesList());
    }

    @Test
    @DisplayName("mapUnitRqToUnit passes a catalog error on")
    void mapUnitRqToUnitSpecialRuleError() {
        when(specialRuleService.findOrCreate("Fear", null)).thenThrow(new IllegalStateException("database down"));

        assertThrows(IllegalStateException.class,
                () -> unitMapper.mapUnitRqToUnit(request(List.of(new SpecialRuleRequest("Fear", null)), stats())));
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
