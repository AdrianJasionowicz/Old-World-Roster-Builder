package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.ResourceNotFoundException;

public class UpgradeNotFoundException extends ResourceNotFoundException {
    public UpgradeNotFoundException(String message) {
        super(message);
    }
}
