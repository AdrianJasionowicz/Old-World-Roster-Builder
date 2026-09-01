package com.armybuilderv2.armyBuilderV2.unit;

import com.armybuilderv2.armyBuilderV2.exception.UnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.unit.model.UnitRequest;
import com.armybuilderv2.armyBuilderV2.unit.model.UnitResponse;
import com.armybuilderv2.armyBuilderV2.unitStats.UnitStats;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitStatsRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @Mock
    UnitRepository unitRepository;

    @Mock
    UnitMapper unitMapper;

    @InjectMocks
    UnitService unitService;


    @Test
    @DisplayName("Should create unit")
    void shouldCreateUnit() {

        // given
        UnitStats unitStats = new UnitStats(
                0L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        );

        UnitStatsRequest unitStatsRequest = new UnitStatsRequest(
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        );

        UnitRequest unitRequest = new UnitRequest(
                "test",
                2.0,
                20,
                UnitType.CORE,
                UnitFaction.EMPIRE,
                new ArrayList<>(),
                unitStatsRequest,
                new ArrayList<>()
        );

        Unit unit = new Unit(
                1L,
                "test",
                2.0,
                20,
                UnitType.CORE,
                UnitFaction.EMPIRE,
                new ArrayList<>(),
                unitStats,
                new ArrayList<>()
        );

        UnitResponse expectedResponse = new UnitResponse(
                1L,
                "test",
                2.0,
                0.0,
                UnitType.CORE
        );

        when(unitMapper.mapUnitRqToUnit(unitRequest))
                .thenReturn(unit);

        when(unitRepository.save(unit))
                .thenReturn(unit);

        when(unitMapper.mapUnitToUnitResponse(unit))
                .thenReturn(expectedResponse);

        // when
        UnitResponse result = unitService.addUnit(unitRequest);

        // then
        assertEquals(expectedResponse, result);

        verify(unitMapper)
                .mapUnitRqToUnit(unitRequest);

        verify(unitRepository)
                .save(unit);

        verify(unitMapper)
                .mapUnitToUnitResponse(unit);
    }


    @Test
    @DisplayName("Should return unit by id")
    void shouldReturnUnitById() {

        // given
        Long unitId = 1L;

        UnitStats unitStats = new UnitStats(
                1L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        );

        Unit unit = new Unit(
                unitId,
                "test",
                2.0,
                20,
                UnitType.CORE,
                UnitFaction.EMPIRE,
                new ArrayList<>(),
                unitStats,
                new ArrayList<>()
        );

        UnitResponse expectedResponse = new UnitResponse(
                unitId,
                "test",
                2.0,
                0.0,
                UnitType.CORE
        );

        when(unitRepository.findById(unitId))
                .thenReturn(Optional.of(unit));

        when(unitMapper.mapUnitToUnitResponse(unit))
                .thenReturn(expectedResponse);

        // when
        UnitResponse result = unitService.getUnitById(unitId);

        // then
        assertEquals(expectedResponse, result);

        verify(unitRepository)
                .findById(unitId);

        verify(unitMapper)
                .mapUnitToUnitResponse(unit);
    }


    @Test
    @DisplayName("Should return units by faction")
    void shouldReturnUnitsByFaction() {

        // given
        String faction = "SKAVEN";
        UnitFaction factionToSearch = UnitFaction.SKAVEN;

        UnitStats unitStats = new UnitStats(
                0L, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        );

        Unit unit1 = new Unit(
                1L,
                "test1",
                2.0,
                20,
                UnitType.CORE,
                UnitFaction.SKAVEN,
                new ArrayList<>(),
                unitStats,
                new ArrayList<>()
        );

        Unit unit2 = new Unit(
                2L,
                "test2",
                2.0,
                20,
                UnitType.CORE,
                UnitFaction.SKAVEN,
                new ArrayList<>(),
                unitStats,
                new ArrayList<>()
        );

        Unit unit3 = new Unit(
                3L,
                "test3",
                2.0,
                20,
                UnitType.CORE,
                UnitFaction.SKAVEN,
                new ArrayList<>(),
                unitStats,
                new ArrayList<>()
        );

        List<Unit> unitsList = List.of(
                unit1,
                unit2,
                unit3
        );

        UnitResponse response1 = new UnitResponse(
                1L,
                "test1",
                2.0,
                0.0,
                UnitType.CORE
        );

        UnitResponse response2 = new UnitResponse(
                2L,
                "test2",
                2.0,
                0.0,
                UnitType.CORE
        );

        UnitResponse response3 = new UnitResponse(
                3L,
                "test3",
                2.0,
                0.0,
                UnitType.CORE
        );

        List<UnitResponse> expected = List.of(
                response1,
                response2,
                response3
        );

        when(unitRepository.getAllByUnitFaction(factionToSearch))
                .thenReturn(unitsList);

        when(unitMapper.mapUnitToUnitResponse(unit1))
                .thenReturn(response1);

        when(unitMapper.mapUnitToUnitResponse(unit2))
                .thenReturn(response2);

        when(unitMapper.mapUnitToUnitResponse(unit3))
                .thenReturn(response3);

        // when
        List<UnitResponse> result =
                unitService.getAllByFaction(faction);

        // then
        assertEquals(expected, result);

        verify(unitRepository)
                .getAllByUnitFaction(factionToSearch);

        verify(unitMapper)
                .mapUnitToUnitResponse(unit1);

        verify(unitMapper)
                .mapUnitToUnitResponse(unit2);

        verify(unitMapper)
                .mapUnitToUnitResponse(unit3);
    }


    @Test
    @DisplayName("Should throw exception when unit does not exist")
    void shouldThrowWhenUnitDoesNotExist() {

        // given
        Long unitId = 10000L;

        when(unitRepository.findById(unitId))
                .thenReturn(Optional.empty());

        // when & then
        assertThrows(
                UnitNotFoundException.class,
                () -> unitService.getUnitById(unitId)
        );
    }
}