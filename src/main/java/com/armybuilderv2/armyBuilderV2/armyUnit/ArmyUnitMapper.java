package com.armybuilderv2.armyBuilderV2.armyUnit;

import com.armybuilderv2.armyBuilderV2.armyUnit.model.ArmyUnitResponse;
import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeMapper;
import com.armybuilderv2.armyBuilderV2.upgrade.model.UpgradeView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmyUnitMapper {

    private final UpgradeMapper upgradeMapper;

    public ArmyUnitMapper(UpgradeMapper upgradeMapper) {
        this.upgradeMapper = upgradeMapper;
    }

    ArmyUnitResponse entityToResponse(ArmyUnit armyUnit) {
        List<Upgrade> upgradeList = armyUnit.getUnit().getUpgradesList();
        List<UpgradeView> upgradeViewList = upgradeList.stream()
                .map(upgradeMapper::entityToView)
                .toList();
        return new ArmyUnitResponse(armyUnit.getId(), armyUnit.getUnit().getName(),armyUnit.getQuantity(),armyUnit.getTotalCost(),upgradeViewList);

    }
}
