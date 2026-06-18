package com.armybuilderv2.armyBuilderV2.unit;

import com.armybuilderv2.armyBuilderV2.unit.model.UnitRequest;
import com.armybuilderv2.armyBuilderV2.unit.model.UnitResponse;
import org.springframework.stereotype.Service;

@Service
public class UnitMapper {

    public Unit mapUnitRqToUnit(UnitRequest unitRequest) {
        Unit unit = new Unit();
        unit.setName(unitRequest.name());
        unit.setUnitType(unitRequest.unitType());
        unit.setUnitFaction(unitRequest.unitFaction());
        unit.setPointsCostPerUnit(unitRequest.pointsCostPerUnit());
        unit.setMinQuantity(unitRequest.minQuantity());
        return unit;
    }

    public UnitResponse mapUnitToUnitResponse(Unit unit) {
       return new UnitResponse(unit.getId(), unit.getName(), unit.getPointsCostPerUnit(),unit.getMinQuantity()*unit.getPointsCostPerUnit());
    }






}
