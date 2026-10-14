package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class ArmyUnitSizeCannotBeChangedException extends BusinessRuleException {
    public ArmyUnitSizeCannotBeChangedException(String message) {
        super(message);
    }
}
