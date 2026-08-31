package com.armybuilderv2.armyBuilderV2.unit;

import com.armybuilderv2.armyBuilderV2.specialRule.SpecialRule;
import com.armybuilderv2.armyBuilderV2.specialRule.SpecialRuleRequest;
import com.armybuilderv2.armyBuilderV2.unit.model.UnitRequest;
import com.armybuilderv2.armyBuilderV2.unit.model.UnitResponse;
import com.armybuilderv2.armyBuilderV2.unitStats.UnitStats;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitStatsRequest;
import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import com.armybuilderv2.armyBuilderV2.upgrade.model.UpgradeRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnitMapper {

    public Unit mapUnitRqToUnit(UnitRequest unitRequest) {
        Unit unit = new Unit();
        unit.setName(unitRequest.name());
        unit.setUnitType(unitRequest.unitType());
        unit.setUnitFaction(unitRequest.unitFaction());
        unit.setPointsCostPerUnit(unitRequest.pointsCostPerUnit());
        unit.setMinQuantity(unitRequest.minQuantity());

        UnitStats unitStats = createUnitStats(unitRequest.unitStatsRequest());
        unit.setUnitStats(unitStats);

        unit.setSpecialRulesList(mapSpecialRuleList(unitRequest.specialRuleList()));
        List<Upgrade> upgradeList = mapUpgradeList(unitRequest.upgradesList());
        upgradeList.forEach(upgrade -> upgrade.setUnit(unit));
        unit.setUpgradesList(upgradeList);
        return unit;
    }


    public UnitResponse mapUnitToUnitResponse(Unit unit) {
       return new UnitResponse(unit.getId(), unit.getName(), unit.getPointsCostPerUnit(),unit.getMinQuantity()*unit.getPointsCostPerUnit(),unit.getUnitType());
    }

    public UnitStats createUnitStats(UnitStatsRequest unitStatsRequest) {
        UnitStats unitStats = new UnitStats();
        unitStats.setWs(unitStatsRequest.ws());
        unitStats.setBs(unitStatsRequest.bs());
        unitStats.setM(unitStatsRequest.m());
        unitStats.setS(unitStatsRequest.s());
        unitStats.setT(unitStatsRequest.t());
        unitStats.setW(unitStatsRequest.w());
        unitStats.setI(unitStatsRequest.i());
        unitStats.setA(unitStatsRequest.a());
        unitStats.setLd(unitStatsRequest.ld());
        unitStats.setBasicSave(unitStatsRequest.basicSave());
        unitStats.setWardSave(unitStatsRequest.wardSave());
        return unitStats;
    }

    public List<SpecialRule> mapSpecialRuleList(List<SpecialRuleRequest> specialRuleList) {

        List<SpecialRule> specialRules = new ArrayList<>();
        for (SpecialRuleRequest specialRuleRequest : specialRuleList) {
            SpecialRule specialRule = new SpecialRule();
            specialRule.setName(specialRuleRequest.name());
            specialRule.setDescription(specialRuleRequest.description());
            specialRules.add(specialRule);
        }
        return specialRules;
    }

    public List<Upgrade> mapUpgradeList(List<UpgradeRequest> upgradeList) {
        List<Upgrade> upgrades = new ArrayList<>();
        for (UpgradeRequest upgradeRequest : upgradeList) {
            Upgrade upgrade = new Upgrade();
            upgrade.setName(upgradeRequest.name());
            upgrade.setDescription(upgradeRequest.description());
            upgrade.setUpgradeType(upgradeRequest.upgradeType());
            upgrade.setPointsCost(upgradeRequest.pointsCost());
            upgrades.add(upgrade);
        }
        return upgrades;
    }




}
