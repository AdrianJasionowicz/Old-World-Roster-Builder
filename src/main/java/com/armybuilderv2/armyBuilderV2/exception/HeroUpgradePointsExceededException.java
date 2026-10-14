package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class HeroUpgradePointsExceededException extends BusinessRuleException {
    public HeroUpgradePointsExceededException(String message) {
        super(message);
    }
}
