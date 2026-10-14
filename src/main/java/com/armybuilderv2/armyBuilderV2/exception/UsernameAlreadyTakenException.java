package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.ResourceConflictException;

public class    UsernameAlreadyTakenException extends ResourceConflictException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
