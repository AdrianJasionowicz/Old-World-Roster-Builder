package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.ResourceConflictException;

public class UpgradeAlreadySelectedException extends ResourceConflictException {
    public UpgradeAlreadySelectedException(String message) {
        super(message);
    }
}
