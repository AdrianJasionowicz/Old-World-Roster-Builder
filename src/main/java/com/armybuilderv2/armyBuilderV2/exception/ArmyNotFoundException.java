package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.ResourceNotFoundException;

public class ArmyNotFoundException extends ResourceNotFoundException {
    public ArmyNotFoundException(String message) {
        super(message);
    }
}
