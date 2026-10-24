package com.oldworld.rosterbuilder.unitStats;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsCalculatorValidatorServiceTest {

    private final StatsCalculatorValidatorService validator = new StatsCalculatorValidatorService();

    @Test
    @DisplayName("toCalculationSave keeps a real save")
    void toCalculationSave() {
        assertEquals(4, validator.toCalculationSave(4));
    }

    @Test
    @DisplayName("toCalculationSave turns a missing save into 7 (no save)")
    void toCalculationSaveNull() {
        assertEquals(7, validator.toCalculationSave(null));
    }

    @Test
    @DisplayName("toCalculationSave turns 0 from the catalog into 7 (no save)")
    void toCalculationSaveZero() {
        assertEquals(7, validator.toCalculationSave(0));
    }

    @Test
    @DisplayName("toDisplaySave keeps a save between 2+ and 6+")
    void toDisplaySave() {
        assertEquals(2, validator.toDisplaySave(2));
        assertEquals(6, validator.toDisplaySave(6));
    }

    @Test
    @DisplayName("toDisplaySave shows 7 or worse as no save")
    void toDisplaySaveNoSave() {
        assertNull(validator.toDisplaySave(7));
        assertNull(validator.toDisplaySave(9));
    }

    @Test
    @DisplayName("toDisplaySave caps a save better than 2+ at 2+")
    void toDisplaySaveCapped() {
        assertEquals(2, validator.toDisplaySave(1));
        assertEquals(2, validator.toDisplaySave(-1));
    }
}
