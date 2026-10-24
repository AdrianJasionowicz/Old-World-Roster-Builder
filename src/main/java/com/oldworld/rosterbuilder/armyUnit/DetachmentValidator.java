package com.oldworld.rosterbuilder.armyUnit;

import com.oldworld.rosterbuilder.exception.BusinessRuleException;
import com.oldworld.rosterbuilder.unit.Unit;
import org.springframework.stereotype.Service;

import static com.oldworld.rosterbuilder.armyUnit.ArmyUnit.DETACHMENT_MIN_SIZE;

@Service
class DetachmentValidator {

    private static final int MAX_DETACHMENTS = 2;

    void validateNewDetachment(ArmyUnit parent, Unit unit) {
        if (parent.isDetachment()) {
            throw new BusinessRuleException("A detachment cannot have its own detachments");
        }
        if (!parent.getUnit().isCanHaveDetachments()) {
            throw new BusinessRuleException(parent.getUnit().getName() + " cannot have detachments");
        }
        if (!unit.isCanBeDetachment()) {
            throw new BusinessRuleException(unit.getName() + " cannot be taken as a detachment");
        }
        if (parent.getDetachments().size() >= MAX_DETACHMENTS) {
            throw new BusinessRuleException("A regiment can have at most " + MAX_DETACHMENTS + " detachments");
        }
        if (parent.getQuantity() < 2 * DETACHMENT_MIN_SIZE) {
            throw new BusinessRuleException("A regiment needs at least " + 2 * DETACHMENT_MIN_SIZE + " models to have a detachment");
        }
    }

    void validateSize(ArmyUnit armyUnit) {
        if (armyUnit.isDetachment() && armyUnit.getQuantity() * 2 > armyUnit.getParent().getQuantity()) {
            throw new BusinessRuleException("A detachment can have at most half as many models as its regiment");
        }
        var largestDetachment = armyUnit.getDetachments().stream().mapToInt(ArmyUnit::getQuantity).max().orElse(0);
        if (largestDetachment * 2 > armyUnit.getQuantity()) {
            throw new BusinessRuleException("A regiment must have at least twice as many models as each of its detachments");
        }
    }
}
