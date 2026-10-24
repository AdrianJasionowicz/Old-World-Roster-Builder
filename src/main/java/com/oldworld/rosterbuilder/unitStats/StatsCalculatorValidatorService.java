package com.oldworld.rosterbuilder.unitStats;

import org.springframework.stereotype.Service;

@Service
class StatsCalculatorValidatorService {

    private static final int MIN_SAVE = 2;
    private static final int NO_SAVE = 7;

    int toCalculationSave(Integer save) {
        if (save == null || save == 0) {
            return NO_SAVE;
        }
        return save;
    }

    Integer toDisplaySave(int save) {
        if (save >= NO_SAVE) {
            return null;
        }
        return Math.max(save, MIN_SAVE);
    }
}
