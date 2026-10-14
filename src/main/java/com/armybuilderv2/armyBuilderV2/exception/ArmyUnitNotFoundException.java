package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.ResourceNotFoundException;

public class ArmyUnitNotFoundException extends ResourceNotFoundException {
    public ArmyUnitNotFoundException(String message) {
        super(message);
    }
}
