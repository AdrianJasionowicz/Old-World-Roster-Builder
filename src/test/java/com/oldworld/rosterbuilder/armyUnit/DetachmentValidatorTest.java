package com.oldworld.rosterbuilder.armyUnit;

import com.oldworld.rosterbuilder.exception.BusinessRuleException;
import com.oldworld.rosterbuilder.unit.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetachmentValidatorTest {

    private final DetachmentValidator detachmentValidator = new DetachmentValidator();

    private static Unit unit(String name, boolean canHaveDetachments, boolean canBeDetachment) {
        var unit = new Unit();
        unit.setName(name);
        unit.setCanHaveDetachments(canHaveDetachments);
        unit.setCanBeDetachment(canBeDetachment);
        return unit;
    }

    private static ArmyUnit regiment(int quantity) {
        var armyUnit = new ArmyUnit();
        armyUnit.setUnit(unit("Swordsmen", true, false));
        armyUnit.setQuantity(quantity);
        return armyUnit;
    }

    private static ArmyUnit detachmentOf(ArmyUnit regiment, int quantity) {
        var detachment = new ArmyUnit();
        detachment.setUnit(unit("Handgunners", false, true));
        detachment.setQuantity(quantity);
        detachment.setParent(regiment);
        regiment.getDetachments().add(detachment);
        return detachment;
    }

    private void assertRejected(String message, Runnable validation) {
        var exception = assertThrows(BusinessRuleException.class, validation::run);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("validateNewDetachment accepts a detachment for a regiment of 10 models")
    void validateNewDetachment() {
        assertDoesNotThrow(() -> detachmentValidator.validateNewDetachment(regiment(10), unit("Handgunners", false, true)));
    }

    @Test
    @DisplayName("validateNewDetachment accepts a second detachment")
    void validateNewDetachmentSecond() {
        var regiment = regiment(20);
        detachmentOf(regiment, 5);

        assertDoesNotThrow(() -> detachmentValidator.validateNewDetachment(regiment, unit("Archers", false, true)));
    }

    @Test
    @DisplayName("validateNewDetachment rejects a detachment of a detachment")
    void validateNewDetachmentOfDetachment() {
        var detachment = detachmentOf(regiment(20), 5);

        assertRejected("A detachment cannot have its own detachments",
                () -> detachmentValidator.validateNewDetachment(detachment, unit("Archers", false, true)));
    }

    @Test
    @DisplayName("validateNewDetachment rejects a regiment that cannot have detachments")
    void validateNewDetachmentNotARegiment() {
        var knights = new ArmyUnit();
        knights.setUnit(unit("Knights of the Empire", false, false));
        knights.setQuantity(10);

        assertRejected("Knights of the Empire cannot have detachments",
                () -> detachmentValidator.validateNewDetachment(knights, unit("Archers", false, true)));
    }

    @Test
    @DisplayName("validateNewDetachment rejects a unit that cannot be a detachment")
    void validateNewDetachmentWrongUnit() {
        assertRejected("Knights of the Empire cannot be taken as a detachment",
                () -> detachmentValidator.validateNewDetachment(regiment(20), unit("Knights of the Empire", false, false)));
    }

    @Test
    @DisplayName("validateNewDetachment rejects a third detachment")
    void validateNewDetachmentThird() {
        var regiment = regiment(30);
        detachmentOf(regiment, 5);
        detachmentOf(regiment, 5);

        assertRejected("A regiment can have at most 2 detachments",
                () -> detachmentValidator.validateNewDetachment(regiment, unit("Archers", false, true)));
    }

    @Test
    @DisplayName("validateNewDetachment rejects a regiment smaller than 10 models")
    void validateNewDetachmentSmallRegiment() {
        assertRejected("A regiment needs at least 10 models to have a detachment",
                () -> detachmentValidator.validateNewDetachment(regiment(9), unit("Handgunners", false, true)));
    }

    @Test
    @DisplayName("validateSize accepts a detachment of exactly half of its regiment")
    void validateSizeHalf() {
        var regiment = regiment(20);
        var detachment = detachmentOf(regiment, 10);

        assertDoesNotThrow(() -> detachmentValidator.validateSize(detachment));
        assertDoesNotThrow(() -> detachmentValidator.validateSize(regiment));
    }

    @Test
    @DisplayName("validateSize accepts a unit without detachments of any size")
    void validateSizeWithoutDetachments() {
        assertDoesNotThrow(() -> detachmentValidator.validateSize(regiment(1)));
    }

    @Test
    @DisplayName("validateSize rejects a detachment over half of its regiment")
    void validateSizeDetachmentTooBig() {
        var detachment = detachmentOf(regiment(20), 11);

        assertRejected("A detachment can have at most half as many models as its regiment",
                () -> detachmentValidator.validateSize(detachment));
    }

    @Test
    @DisplayName("validateSize rejects a regiment smaller than twice its largest detachment")
    void validateSizeRegimentTooSmall() {
        var regiment = regiment(15);
        detachmentOf(regiment, 5);
        detachmentOf(regiment, 8);

        assertRejected("A regiment must have at least twice as many models as each of its detachments",
                () -> detachmentValidator.validateSize(regiment));
    }
}
