package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.ArmyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArmyMapper {
    @Autowired
    private ArmyUnitResponseMapper armyUnitResponseMapper;



    public ArmyView makeView(Army army) {
        if (army == null) {
            throw new IllegalArgumentException("Army cannot be null");
        }
        ArmyView armyView = new ArmyView();
        armyView.setId(army.getId());
        armyView.setName(army.getName());
        armyView.setDescription(army.getDescription());
        armyView.setPointsLimit(army.getPointsLimit());
        armyView.setFaction(army.getFaction());
        armyView.setArmyUnitResponseList(army.getArmyUnitsList().stream()
                .map(armyUnitResponseMapper::makeView)
                .toList());

        return armyView;
    }


}
