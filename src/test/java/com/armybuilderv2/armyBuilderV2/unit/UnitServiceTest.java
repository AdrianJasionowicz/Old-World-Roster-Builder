package com.armybuilderv2.armyBuilderV2.unit;

import com.armybuilderv2.armyBuilderV2.unit.model.UnitRequest;
import com.armybuilderv2.armyBuilderV2.unit.model.UnitResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @Mock
    UnitRepository unitRepository;
    @InjectMocks
    UnitService unitService;
    @Mock
    UnitMapper unitMapper;


    @Test
    @DisplayName("Should Create Unit")
    void shouldCreateUnit() {
        UnitRequest unitRequest = new UnitRequest("test", 2.0, 20, UnitType.CORE, UnitFaction.EMPIRE);
        Unit unit = new Unit(1L, "test", 2.0, 20, UnitType.CORE, UnitFaction.EMPIRE, new ArrayList<>());
        UnitResponse response = new UnitResponse(1L, "test", 2.0);
        when(unitMapper.mapUnitRqToUnit(unitRequest)).thenReturn(unit);
        when(unitRepository.save(unit)).thenReturn(unit);
        when(unitMapper.mapUnitToUnitResponse(unit)).thenReturn(response);
        UnitResponse result = unitService.addUnit(unitRequest);
        verify(unitMapper)
                .mapUnitRqToUnit(unitRequest);

        verify(unitRepository)
                .save(unit);

        verify(unitMapper)
                .mapUnitToUnitResponse(unit);

        assertEquals(response, result);
    }

    @Test
    @DisplayName("Should return unit by id")
    void shouldReturnUnitById() {
        Unit unit = new Unit(1L, "test", 2.0, 20, UnitType.CORE, UnitFaction.EMPIRE, new ArrayList<>());
        UnitResponse response = new UnitResponse(1L, "test", 2.0);
        when(unitRepository.getReferenceById(1L)).thenReturn(unit);
        when(unitMapper.mapUnitToUnitResponse(unit)).thenReturn(response);
        UnitResponse result = unitService.getUnitById(1L);

        assertEquals(response, result);
        verify(unitRepository).getReferenceById(1L);
        verify(unitMapper).mapUnitToUnitResponse(unit);
    }

    @Test
    @DisplayName("Should return units by faction")
    void shouldReturnUnits() {

        // given
        String faction = "skaven";
        UnitFaction factionToSearch = UnitFaction.SKAVEN;

        Unit unit1 =
                new Unit(1L, "test1", 2.0, 20,
                        UnitType.CORE,
                        UnitFaction.SKAVEN,
                        new ArrayList<>());

        Unit unit2 =
                new Unit(2L, "test2", 2.0, 20,
                        UnitType.CORE,
                        UnitFaction.SKAVEN,
                        new ArrayList<>());

        Unit unit3 =
                new Unit(3L, "test3", 2.0, 20,
                        UnitType.CORE,
                        UnitFaction.SKAVEN,
                        new ArrayList<>());

        List<Unit> unitsList =
                List.of(unit1, unit2, unit3);

        UnitResponse response1 =
                new UnitResponse(1L, "test1", 2.0);

        UnitResponse response2 =
                new UnitResponse(2L, "test2", 2.0);

        UnitResponse response3 =
                new UnitResponse(3L, "test3", 2.0);

        List<UnitResponse> expected =
                List.of(response1, response2, response3);

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
    @DisplayName("Should throw exception when unit not exist")
    void shouldThrowWhenUnitDoesNotExist() {
        when(unitRepository.getReferenceById(1L)).thenReturn(null);
        assertThrows(IllegalArgumentException.class,()-> unitService.getUnitById(1L));
    }


    @Test
    void getAllByFaction() {
    }

    @Test
    void getUnitById() {
    }

    @Test
    void deleteById() {
    }
}