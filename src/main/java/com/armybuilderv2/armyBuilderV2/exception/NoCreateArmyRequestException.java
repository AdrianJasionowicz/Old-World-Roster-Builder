package com.armybuilderv2.armyBuilderV2.exception;

public class NoCreateArmyRequestException extends RuntimeException {
    public NoCreateArmyRequestException(String message) {
        super("The request form is invalid: " + message);
    }
}
