package com.armybuilderv2.armyBuilderV2.exception;

public class UserNoLoggedInException extends RuntimeException {
    public UserNoLoggedInException(String message) {
        super(message);
    }
}
