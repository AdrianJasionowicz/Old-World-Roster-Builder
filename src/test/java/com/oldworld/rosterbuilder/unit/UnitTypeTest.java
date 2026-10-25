package com.oldworld.rosterbuilder.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.oldworld.rosterbuilder.unit.UnitType.*;
import static org.junit.jupiter.api.Assertions.*;

class UnitTypeTest {

    @Test
    @DisplayName("pointsLimit is the share of the army limit")
    void pointsLimit() {
        assertEquals(500, CORE.pointsLimit(2000));
        assertEquals(1000, SPECIAL.pointsLimit(2000));
        assertEquals(0, RARE.pointsLimit(0));
    }

    @Test
    @DisplayName("isWithinLimit: core is a minimum, reaching it exactly is enough")
    void isWithinLimitCore() {
        assertTrue(CORE.isWithinLimit(500, 2000));
        assertFalse(CORE.isWithinLimit(499, 2000));
    }

    @Test
    @DisplayName("isWithinLimit: rare is a maximum, going over it is not allowed")
    void isWithinLimitRare() {
        assertTrue(RARE.isWithinLimit(500, 2000));
        assertFalse(RARE.isWithinLimit(501, 2000));
    }

    @Test
    @DisplayName("duplicateLimit doubles from 3000 points")
    void duplicateLimit() {
        assertEquals(3, SPECIAL.duplicateLimit(2999));
        assertEquals(6, SPECIAL.duplicateLimit(3000));
        assertEquals(4, RARE.duplicateLimit(4000));
    }

    @Test
    @DisplayName("only special and rare units have a copy limit")
    void hasDuplicateLimit() {
        assertTrue(SPECIAL.hasDuplicateLimit());
        assertTrue(RARE.hasDuplicateLimit());
        assertFalse(CORE.hasDuplicateLimit());
        assertFalse(LORDS.hasDuplicateLimit());
    }

    @Test
    @DisplayName("lords and heroes are characters with their own magic items limit")
    void characters() {
        assertTrue(LORDS.isCharacter());
        assertTrue(HERO.isCharacter());
        assertFalse(CORE.isCharacter());
        assertEquals(100, LORDS.getMagicItemsPointsLimit());
        assertEquals(50, HERO.getMagicItemsPointsLimit());
        assertEquals(0, RARE.getMagicItemsPointsLimit());
    }

    @Test
    @DisplayName("every unit type except characters can change size")
    void canChangeSize() {
        assertTrue(CORE.canChangeSize());
        assertTrue(SPECIAL.canChangeSize());
        assertTrue(RARE.canChangeSize());
        assertFalse(HERO.canChangeSize());
        assertFalse(LORDS.canChangeSize());
    }
}
