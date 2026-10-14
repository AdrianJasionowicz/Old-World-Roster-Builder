package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class EmptyRegisterRequestException extends BusinessRuleException {
    public EmptyRegisterRequestException(String message) {
        super(message);
    }
}
