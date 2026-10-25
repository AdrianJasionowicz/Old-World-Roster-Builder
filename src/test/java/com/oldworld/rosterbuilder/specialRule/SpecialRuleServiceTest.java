package com.oldworld.rosterbuilder.specialRule;

import com.oldworld.rosterbuilder.army.Faction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialRuleServiceTest {

    @Mock
    SpecialRuleRepository specialRuleRepository;
    @InjectMocks
    SpecialRuleService specialRuleService;

    @Test
    @DisplayName("findOrCreate returns a rule that is already in the catalog")
    void findOrCreateExisting() {
        var fear = new SpecialRule(1L, "Fear", "Causes Fear.");
        when(specialRuleRepository.findByName("Fear")).thenReturn(Optional.of(fear));

        assertSame(fear, specialRuleService.findOrCreate("Fear", "Other description"));
        verify(specialRuleRepository, never()).save(any());
    }

    @Test
    @DisplayName("findOrCreate saves a new rule with a trimmed name")
    void findOrCreateNew() {
        when(specialRuleRepository.findByName("Stubborn")).thenReturn(Optional.empty());
        when(specialRuleRepository.save(any(SpecialRule.class))).then(returnsFirstArg());

        var rule = specialRuleService.findOrCreate(" Stubborn ", "Tests Leadership unmodified.");

        assertEquals("Stubborn", rule.getName());
        assertEquals("Tests Leadership unmodified.", rule.getDescription());
        verify(specialRuleRepository).save(rule);
    }

    @Test
    @DisplayName("findOrCreate passes a database error on")
    void findOrCreateDatabaseError() {
        when(specialRuleRepository.findByName("Fear")).thenReturn(Optional.empty());
        when(specialRuleRepository.save(any(SpecialRule.class))).thenThrow(new DataIntegrityViolationException("duplicate"));

        assertThrows(DataIntegrityViolationException.class, () -> specialRuleService.findOrCreate("Fear", null));
    }

    @Test
    @DisplayName("findOrCreate without a name throws NullPointerException")
    void findOrCreateNullName() {
        assertThrows(NullPointerException.class, () -> specialRuleService.findOrCreate(null, "Causes Fear."));
        verifyNoInteractions(specialRuleRepository);
    }

    @Test
    @DisplayName("getFactionRules returns the rules of the faction in order")
    void getFactionRules() {
        when(specialRuleRepository.findFactionRules("SKAVEN")).thenReturn(List.of(
                new SpecialRule(1L, "Strength in Numbers", "Rank bonus to Leadership."),
                new SpecialRule(2L, "Scurry Away", "Extra flee dice.")));

        assertEquals(List.of(
                new SpecialRuleView("Strength in Numbers", "Rank bonus to Leadership."),
                new SpecialRuleView("Scurry Away", "Extra flee dice.")), specialRuleService.getFactionRules(Faction.SKAVEN));
    }

    @Test
    @DisplayName("getFactionRules of a faction without rules is empty")
    void getFactionRulesEmpty() {
        when(specialRuleRepository.findFactionRules("EMPIRE")).thenReturn(List.of());

        assertTrue(specialRuleService.getFactionRules(Faction.EMPIRE).isEmpty());
    }

    @Test
    @DisplayName("getFactionRules without a faction throws NullPointerException")
    void getFactionRulesNullFaction() {
        assertThrows(NullPointerException.class, () -> specialRuleService.getFactionRules(null));
        verifyNoInteractions(specialRuleRepository);
    }
}
