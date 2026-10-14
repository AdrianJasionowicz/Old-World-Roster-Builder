package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class LordsUpgradePointsExceededException extends BusinessRuleException {
    public LordsUpgradePointsExceededException(String message) {
        super(message);
    }
}
