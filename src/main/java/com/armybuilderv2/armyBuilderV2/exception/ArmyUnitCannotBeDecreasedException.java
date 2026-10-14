package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class ArmyUnitCannotBeDecreasedException extends BusinessRuleException {
    public ArmyUnitCannotBeDecreasedException(String message) {
        super(message);
    }
}
