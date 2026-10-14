package com.armybuilderv2.armyBuilderV2.exception.base;

public abstract class BusinessRuleException extends RuntimeException {

    protected BusinessRuleException(String message) {
        super(message);
    }
}