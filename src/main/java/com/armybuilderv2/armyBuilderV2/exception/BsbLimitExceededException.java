package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class BsbLimitExceededException extends BusinessRuleException {
    public BsbLimitExceededException(String message) {
        super(message);
    }
}
