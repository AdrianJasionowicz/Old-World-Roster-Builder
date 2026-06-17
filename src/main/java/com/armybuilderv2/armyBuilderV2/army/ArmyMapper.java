package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.ArmyView;
import org.springframework.stereotype.Service;

@Service
public class ArmyMapper {

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
        return armyView;
    }





}
