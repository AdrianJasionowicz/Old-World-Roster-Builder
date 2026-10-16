package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class IllegalActionException extends BusinessRuleException {
    public IllegalActionException(String message) {
        super(message);
    }
}
