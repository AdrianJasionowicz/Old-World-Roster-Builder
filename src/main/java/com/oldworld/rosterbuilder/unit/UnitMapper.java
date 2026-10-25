package com.oldworld.rosterbuilder.unit;

import com.oldworld.rosterbuilder.specialRule.SpecialRule;
import com.oldworld.rosterbuilder.specialRule.SpecialRuleRequest;
import com.oldworld.rosterbuilder.unit.model.UnitRequest;
import com.oldworld.rosterbuilder.unit.model.UnitResponse;
import com.oldworld.rosterbuilder.unitStats.UnitStats;
import com.oldworld.rosterbuilder.unitStats.model.UnitStatsRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
class UnitMapper {

    Unit mapUnitRqToUnit(UnitRequest unitRequest) {
        var unit = new Unit();
        unit.setName(unitRequest.name());
        unit.setUnitType(unitRequest.unitType());
        unit.setUnitFaction(unitRequest.unitFaction());
        unit.setPointsCostPerUnit(unitRequest.pointsCostPerUnit());
        unit.setMinQuantity(unitRequest.minQuantity());
        unit.setFixedSize(unitRequest.fixedSize());
        unit.setUnitStats(createUnitStats(unitRequest.unitStatsRequest()));
        unit.setSpecialRulesList(mapSpecialRules(unitRequest.specialRuleList()));
        return unit;
    }

    UnitResponse mapUnitToUnitResponse(Unit unit) {
        return new UnitResponse(unit.getId(), unit.getName(), unit.getPointsCostPerUnit(), unit.getMinQuantity() * unit.getPointsCostPerUnit(),
                unit.getUnitType(), unit.isCanBeDetachment());
    }

    private UnitStats createUnitStats(UnitStatsRequest request) {
        var unitStats = new UnitStats();
        unitStats.setM(request.m());
        unitStats.setWs(request.ws());
        unitStats.setBs(request.bs());
        unitStats.setS(request.s());
        unitStats.setT(request.t());
        unitStats.setW(request.w());
        unitStats.setI(request.i());
        unitStats.setA(request.a());
        unitStats.setLd(request.ld());
        unitStats.setBasicSave(request.basicSave());
        unitStats.setWardSave(request.wardSave());
        return unitStats;
    }

    private List<SpecialRule> mapSpecialRules(List<SpecialRuleRequest> requests) {
        if (requests == null) {
            return new ArrayList<>();
        }
        return requests.stream()
                .map(request -> new SpecialRule(null, request.name(), request.description()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
