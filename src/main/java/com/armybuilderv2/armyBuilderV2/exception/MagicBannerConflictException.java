package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;

public class MagicBannerConflictException extends BusinessRuleException {
    public MagicBannerConflictException(String message) {
        super(message);
    }
}
