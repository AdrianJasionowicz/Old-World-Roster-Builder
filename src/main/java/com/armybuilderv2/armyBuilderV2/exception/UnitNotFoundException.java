package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.ResourceNotFoundException;

public class UnitNotFoundException extends ResourceNotFoundException {
    public UnitNotFoundException(String message) {
        super(message);
    }
}
