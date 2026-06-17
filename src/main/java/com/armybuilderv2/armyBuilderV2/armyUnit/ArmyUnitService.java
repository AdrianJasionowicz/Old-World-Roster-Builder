package com.armybuilderv2.armyBuilderV2.armyUnit;

import org.springframework.stereotype.Service;

@Service
public class ArmyUnitService {

    private ArmyUnitRepository armyUnitRepository;


    public ArmyUnitService(ArmyUnitRepository armyUnitRepository) {
        this.armyUnitRepository = armyUnitRepository;
    }
}
