package com.armybuilderv2.armyBuilderV2.armyUnit;

import com.armybuilderv2.armyBuilderV2.armyUnit.model.ArmyUnitResponse;
import com.armybuilderv2.armyBuilderV2.unitStats.StatsCalculatorService;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitDetails;
import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeMapper;
import com.armybuilderv2.armyBuilderV2.upgrade.model.UpgradeView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmyUnitMapper {

    private final UpgradeMapper upgradeMapper;
    private final StatsCalculatorService statsCalculatorService;

    public ArmyUnitMapper(UpgradeMapper upgradeMapper, StatsCalculatorService statsCalculatorService) {
        this.upgradeMapper = upgradeMapper;
        this.statsCalculatorService = statsCalculatorService;
    }

    ArmyUnitResponse entityToResponse(ArmyUnit armyUnit) {
        List<Upgrade> upgradeList = armyUnit.getUnit().getUpgradesList();
        List<UpgradeView> upgradeViewList = upgradeList.stream()
                .map(upgradeMapper::entityToView)
                .toList();
        UnitDetails unitDetails = statsCalculatorService.getUnitDetails(armyUnit.getId());

        return new ArmyUnitResponse(armyUnit.getId(),
                armyUnit.getUnit().getName(),
                armyUnit.getQuantity(),
                armyUnit.getTotalCost(),
                unitDetails.m(),
                unitDetails.ws(),
                unitDetails.bs(),
                unitDetails.s(),
                unitDetails.t(),
                unitDetails.w(),
                unitDetails.i(),
                unitDetails.a(),
                unitDetails.ld(),
                unitDetails.basicSave(),
                unitDetails.wardSave(),
                upgradeViewList,
                armyUnit.getUnit().getUnitType());

    }
}
