package com.armybuilderv2.armyBuilderV2.exception.base;

public abstract class ResourceConflictException extends RuntimeException {

    protected ResourceConflictException(String message) {
        super(message);
    }
}