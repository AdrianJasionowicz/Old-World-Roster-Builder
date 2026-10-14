package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class MagicBannerLimitExceededException extends BusinessRuleException {
    public MagicBannerLimitExceededException(String message) {
        super(message);
    }
}
