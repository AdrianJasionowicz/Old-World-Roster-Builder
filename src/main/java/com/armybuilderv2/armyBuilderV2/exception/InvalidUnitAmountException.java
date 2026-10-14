package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class InvalidUnitAmountException extends BusinessRuleException {
    public InvalidUnitAmountException(String message) {
        super(message);
    }
}
