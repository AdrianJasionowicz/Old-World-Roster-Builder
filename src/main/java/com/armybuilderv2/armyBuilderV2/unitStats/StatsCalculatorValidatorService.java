package com.armybuilderv2.armyBuilderV2.unitStats;

import com.armybuilderv2.armyBuilderV2.unitStats.model.CurrentStats;
import org.springframework.stereotype.Service;

@Service
public class StatsCalculatorValidatorService {

    private static final int MIN_SAVE = 2;
    private static final int DEFAULT_SAVE_IF_NULL = 7;

    public Integer safeCheckCurrentSave(Integer baseValue) {
        if (baseValue == 0) {
            return DEFAULT_SAVE_IF_NULL;
        }
        return baseValue;
    }

    public Integer safeCalculate(Integer baseValue, int modifier) {
        if (baseValue == null) {
            baseValue = DEFAULT_SAVE_IF_NULL;
        }
        return baseValue - modifier;
    }

    public CurrentStats validateSaveToRules(CurrentStats currentStats) {
        if (currentStats.getBasicSave() != null
                && currentStats.getBasicSave() == DEFAULT_SAVE_IF_NULL) {
            currentStats.setBasicSave(null);
        }
        if (currentStats.getBasicSave() != null
                && currentStats.getBasicSave() < MIN_SAVE) {
            currentStats.setBasicSave(MIN_SAVE);
        }
        if (currentStats.getWardSave() != null
                && currentStats.getWardSave() == DEFAULT_SAVE_IF_NULL) {
            currentStats.setWardSave(null);
        }
        if (currentStats.getWardSave() != null
                && currentStats.getWardSave() < MIN_SAVE) {
            currentStats.setWardSave(MIN_SAVE);
        }
        return currentStats;
    }
}