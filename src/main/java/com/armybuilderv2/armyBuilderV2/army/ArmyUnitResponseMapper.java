package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.armyUnit.model.ArmyUnitResponse;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgrade;
import com.armybuilderv2.armyBuilderV2.unitStats.StatsCalculatorService;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitDetails;
import com.armybuilderv2.armyBuilderV2.upgrade.model.UpgradeView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ArmyUnitResponseMapper {


    private final StatsCalculatorService statsCalculatorService;

    public ArmyUnitResponseMapper(StatsCalculatorService statsCalculatorService) {
        this.statsCalculatorService = statsCalculatorService;
    }

    public ArmyUnitResponse makeView(ArmyUnit armyUnit) {
        List<UpgradeView> upgradeViews = makeUpgradeView(armyUnit.getSelectedUpgradesList());
        UnitDetails unitDetails = statsCalculatorService.getUnitDetails(armyUnit.getId());
        ArmyUnitResponse armyUnitResponse = new ArmyUnitResponse(
                armyUnit.getId(),
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
                upgradeViews,
                armyUnit.getUnit().getUnitType());
        return armyUnitResponse;
    }

    private List<UpgradeView> makeUpgradeView(List<SelectedUpgrade> upgradeList) {

        List<UpgradeView> upgradeViewList = new ArrayList<>();
        for (SelectedUpgrade selectedUpgrade : upgradeList) {
            UpgradeView upgradeView = new UpgradeView(
                    selectedUpgrade.getId(),
                    selectedUpgrade.getUpgrade().getName(),
                    selectedUpgrade.getUpgrade().getPointsCost(),
                    selectedUpgrade.getUpgrade().getUpgradeType(),
                    selectedUpgrade.getUpgrade().getDescription()
            );
            upgradeViewList.add(upgradeView);
        }
        return upgradeViewList;
    }


}
