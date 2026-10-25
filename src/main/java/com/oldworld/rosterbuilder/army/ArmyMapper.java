package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.army.model.ArmyView;
import com.oldworld.rosterbuilder.specialRule.SpecialRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArmyMapper {

    private final ArmyUnitResponseMapper armyUnitResponseMapper;
    private final SpecialRuleService specialRuleService;

    public ArmyView makeView(Army army) {
        var armyUnits = army.getArmyUnitsList().stream()
                .map(armyUnitResponseMapper::makeView)
                .toList();
        return new ArmyView(army.getId(), army.getName(), army.getDescription(), army.getPointsLimit(), army.getFaction(), armyUnits,
                specialRuleService.getFactionRules(army.getFaction()));
    }
}
