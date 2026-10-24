package com.oldworld.rosterbuilder.upgrade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.oldworld.rosterbuilder.upgrade.ModifierOperation.*;
import static org.junit.jupiter.api.Assertions.*;

class ModifierOperationTest {

    @Test
    @DisplayName("SET replaces the value, also with a worse one")
    void set() {
        assertEquals(10, SET.apply(4, 10));
        assertEquals(1, SET.apply(4, 1));
    }

    @Test
    @DisplayName("ADD adds the value, a negative value lowers the stat")
    void add() {
        assertEquals(5, ADD.apply(4, 1));
        assertEquals(3, ADD.apply(4, -1));
    }

    @Test
    @DisplayName("IMPROVE lowers a save roll, so the save gets better")
    void improve() {
        assertEquals(4, IMPROVE.apply(5, 1));
        assertEquals(6, IMPROVE.apply(7, 1));
    }

    @Test
    @DisplayName("BEST_OF keeps the lower, so the better, save")
    void bestOf() {
        assertEquals(4, BEST_OF.apply(4, 6));
        assertEquals(5, BEST_OF.apply(7, 5));
    }

    @Test
    @DisplayName("SET goes first, ADD and IMPROVE next, BEST_OF last")
    void phases() {
        assertTrue(SET.getPhase() < ADD.getPhase());
        assertEquals(ADD.getPhase(), IMPROVE.getPhase());
        assertTrue(IMPROVE.getPhase() < BEST_OF.getPhase());
    }
}
