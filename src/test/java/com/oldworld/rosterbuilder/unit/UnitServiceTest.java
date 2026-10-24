package com.oldworld.rosterbuilder.unit;

import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.unit.model.UnitRequest;
import com.oldworld.rosterbuilder.unit.model.UnitResponse;
import com.oldworld.rosterbuilder.unitStats.UnitStats;
import com.oldworld.rosterbuilder.unitStats.model.UnitStatsRequest;
import com.oldworld.rosterbuilder.upgrade.Upgrade;
import com.oldworld.rosterbuilder.upgrade.UpgradeService;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import com.oldworld.rosterbuilder.upgrade.model.UpgradeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @Mock
    UnitRepository unitRepository;

    @Mock
    UnitMapper unitMapper;

    @Mock
    UpgradeService upgradeService;

    @InjectMocks
    UnitService unitService;

    private Unit unit(Long id, String name, UnitFaction faction) {
        Unit unit = new Unit();
        unit.setId(id);
        unit.setName(name);
        unit.setPointsCostPerUnit(2.0);
        unit.setMinQuantity(20);
        unit.setUnitType(UnitType.CORE);
        unit.setUnitFaction(faction);
        unit.setUnitStats(new UnitStats(id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        return unit;
    }

    private UnitRequest unitRequest(List<UpgradeRequest> upgrades) {
        return new UnitRequest(
                "test",
                2.0,
                20,
                UnitType.CORE,
                UnitFaction.EMPIRE,
                new ArrayList<>(),
                new UnitStatsRequest(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                upgrades
        );
    }

    @Test
    @DisplayName("Should create unit")
    void shouldCreateUnit() {
        UnitRequest unitRequest = unitRequest(new ArrayList<>());
        Unit unit = unit(1L, "test", UnitFaction.EMPIRE);
        UnitResponse expectedResponse = new UnitResponse(1L, "test", 2.0, 0.0, UnitType.CORE, false);

        when(unitMapper.mapUnitRqToUnit(unitRequest)).thenReturn(unit);
        when(unitMapper.mapUnitToUnitResponse(unit)).thenReturn(expectedResponse);

        UnitResponse result = unitService.addUnit(unitRequest);

        assertEquals(expectedResponse, result);
        verify(unitRepository).save(unit);
    }

    @Test
    @DisplayName("Should link catalog upgrades to the unit with the unit's own cost")
    void shouldLinkCatalogUpgradesWithCost() {
        UnitRequest unitRequest = unitRequest(List.of(
                new UpgradeRequest("Shield", 0.5, UpgradeType.UNIT_EQUIPMENT, "Shields"),
                new UpgradeRequest("Shield", 0.5, UpgradeType.UNIT_EQUIPMENT, "Shields")
        ));
        Unit unit = unit(1L, "test", UnitFaction.EMPIRE);
        Upgrade shield = new Upgrade("Shield", UpgradeType.UNIT_EQUIPMENT, "Shields");

        when(unitMapper.mapUnitRqToUnit(unitRequest)).thenReturn(unit);
        when(upgradeService.findOrCreate("Shield", UpgradeType.UNIT_EQUIPMENT, "Shields")).thenReturn(shield);

        unitService.addUnit(unitRequest);

        assertEquals(1, unit.getUnitUpgrades().size());
        assertSame(shield, unit.getUnitUpgrades().get(0).getUpgrade());
        assertSame(unit, unit.getUnitUpgrades().get(0).getUnit());
        assertEquals(0.5, unit.getUnitUpgrades().get(0).getPointsCost());
    }

    @Test
    @DisplayName("Should return unit by id")
    void shouldReturnUnitById() {
        Unit unit = unit(1L, "test", UnitFaction.EMPIRE);
        UnitResponse expectedResponse = new UnitResponse(1L, "test", 2.0, 0.0, UnitType.CORE, false);

        when(unitRepository.findById(1L)).thenReturn(Optional.of(unit));
        when(unitMapper.mapUnitToUnitResponse(unit)).thenReturn(expectedResponse);

        assertEquals(expectedResponse, unitService.getUnitById(1L));
    }

    @Test
    @DisplayName("Should return units by faction")
    void shouldReturnUnitsByFaction() {
        Unit unit1 = unit(1L, "test1", UnitFaction.SKAVEN);
        Unit unit2 = unit(2L, "test2", UnitFaction.SKAVEN);
        UnitResponse response1 = new UnitResponse(1L, "test1", 2.0, 0.0, UnitType.CORE, false);
        UnitResponse response2 = new UnitResponse(2L, "test2", 2.0, 0.0, UnitType.CORE, false);

        when(unitRepository.getAllByUnitFaction(UnitFaction.SKAVEN)).thenReturn(List.of(unit1, unit2));
        when(unitMapper.mapUnitToUnitResponse(unit1)).thenReturn(response1);
        when(unitMapper.mapUnitToUnitResponse(unit2)).thenReturn(response2);

        assertEquals(List.of(response1, response2), unitService.getAllByFaction(UnitFaction.SKAVEN));
    }

    @Test
    @DisplayName("Should throw exception when unit does not exist")
    void shouldThrowWhenUnitDoesNotExist() {
        when(unitRepository.findById(10000L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> unitService.getUnitById(10000L));
    }
}
