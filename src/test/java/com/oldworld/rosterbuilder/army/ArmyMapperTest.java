package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.armyUnit.model.ArmyUnitResponse;
import com.oldworld.rosterbuilder.army.model.ArmyView;
import com.oldworld.rosterbuilder.specialRule.SpecialRuleService;
import com.oldworld.rosterbuilder.specialRule.SpecialRuleView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArmyMapperTest {

    @Mock
    ArmyUnitResponseMapper armyUnitResponseMapper;
    @Mock
    SpecialRuleService specialRuleService;
    @InjectMocks
    ArmyMapper armyMapper;

    private static Army army() {
        var army = new Army();
        army.setId(1L);
        army.setName("Clan Mors");
        army.setDescription("Warlord clan");
        army.setPointsLimit(2000.0);
        army.setFaction(Faction.SKAVEN);
        return army;
    }

    @Test
    @DisplayName("makeView maps the army and each of its units in order")
    void makeView() {
        var army = army();
        var first = new ArmyUnit();
        var second = new ArmyUnit();
        army.getArmyUnitsList().addAll(List.of(first, second));
        var firstView = mock(ArmyUnitResponse.class);
        var secondView = mock(ArmyUnitResponse.class);
        when(armyUnitResponseMapper.makeView(first)).thenReturn(firstView);
        when(armyUnitResponseMapper.makeView(second)).thenReturn(secondView);
        var rules = List.of(new SpecialRuleView("Strength in Numbers", "Rank bonus to Leadership."));
        when(specialRuleService.getFactionRules(Faction.SKAVEN)).thenReturn(rules);

        assertEquals(new ArmyView(1L, "Clan Mors", "Warlord clan", 2000.0, Faction.SKAVEN, List.of(firstView, secondView), rules), armyMapper.makeView(army));
    }

    @Test
    @DisplayName("makeView passes an army rules error on")
    void makeViewRulesError() {
        when(specialRuleService.getFactionRules(Faction.SKAVEN)).thenThrow(new IllegalStateException("database down"));

        assertThrows(IllegalStateException.class, () -> armyMapper.makeView(army()));
    }

    @Test
    @DisplayName("makeView of an empty army has no units")
    void makeViewEmpty() {
        var view = armyMapper.makeView(army());

        assertTrue(view.armyUnitResponseList().isEmpty());
        assertTrue(view.armyRules().isEmpty());
        verifyNoInteractions(armyUnitResponseMapper);
    }

    @Test
    @DisplayName("makeView passes a unit mapping error on")
    void makeViewUnitError() {
        var army = army();
        var armyUnit = new ArmyUnit();
        army.getArmyUnitsList().add(armyUnit);
        when(armyUnitResponseMapper.makeView(armyUnit)).thenThrow(new IllegalStateException("broken unit"));

        assertThrows(IllegalStateException.class, () -> armyMapper.makeView(army));
    }
}
