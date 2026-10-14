package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class NoCreateArmyRequestException extends BusinessRuleException {
    public NoCreateArmyRequestException(String message) {
        super("The request form is invalid: " + message);
    }
}
