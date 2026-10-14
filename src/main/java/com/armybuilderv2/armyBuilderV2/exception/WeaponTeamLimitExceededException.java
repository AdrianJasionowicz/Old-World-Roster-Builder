package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class WeaponTeamLimitExceededException extends BusinessRuleException {
    public WeaponTeamLimitExceededException(String message) {
        super(message);
    }
}
