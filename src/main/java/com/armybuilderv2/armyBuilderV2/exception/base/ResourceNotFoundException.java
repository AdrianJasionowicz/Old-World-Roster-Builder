package com.armybuilderv2.armyBuilderV2.exception.base;

public abstract class ResourceNotFoundException extends RuntimeException {
    protected ResourceNotFoundException(String message) {
        super(message);
    }
}