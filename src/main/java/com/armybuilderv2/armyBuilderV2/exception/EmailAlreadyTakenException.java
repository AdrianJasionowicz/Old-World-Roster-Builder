package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.ResourceConflictException;

public class EmailAlreadyTakenException extends ResourceConflictException {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
