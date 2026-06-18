package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnitRepository;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.model.UpgradeViewCombined;
import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import com.armybuilderv2.armyBuilderV2.upgrade.model.UpgradeView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectedUpgradeService {

    private final SelectedUpgradeRepository selectedUpgradeRepository;
    private final ArmyUnitRepository armyUnitRepository;
    private SelectedUpgradeMapper selectedUpgradeMapper;

    public SelectedUpgradeService(SelectedUpgradeRepository selectedUpgradeRepository, ArmyUnitRepository armyUnitRepository, SelectedUpgradeMapper selectedUpgradeMapper) {
        this.selectedUpgradeRepository = selectedUpgradeRepository;
        this.armyUnitRepository = armyUnitRepository;
        this.selectedUpgradeMapper = selectedUpgradeMapper;
    }


    public void selectUpgrade(Long selectedUnitId, Long upgradeId) {

        ArmyUnit armyUnit = armyUnitRepository.getReferenceById(selectedUnitId);
        boolean alreadySelected = armyUnit.getSelectedUpgradesList()
                .stream()
                .anyMatch(su ->
                        su.getUpgrade().getId().equals(upgradeId));

        if (alreadySelected) {
            throw new IllegalArgumentException("Upgrade already selected.");
        }

        List<Upgrade> upgradeList = armyUnit.getUnit().getUpgradesList();

        Upgrade upgradeToSelect = upgradeList
                .stream()
                .filter(upgrade -> upgrade.getId().equals(upgradeId))
                .findFirst()
                .orElseThrow();

        SelectedUpgrade selectedUpgrade =
                selectedUpgradeMapper.mapUpgradeToSelectedUpgrade(upgradeToSelect);

        selectedUpgrade.setArmyUnit(armyUnit);

        armyUnit.getSelectedUpgradesList()
                .add(selectedUpgrade);

        armyUnitRepository.save(armyUnit);
    }


    public List<UpgradeViewCombined> getUpgradeView(Long armyUnitId) {
        ArmyUnit armyUnit = armyUnitRepository.getReferenceById(armyUnitId);

        List<SelectedUpgrade> selectedUpgradeList =
                armyUnit.getSelectedUpgradesList();

        List<Upgrade> upgradeList =
                armyUnit.getUnit().getUpgradesList();

        return upgradeList.stream()
                .map(upgrade -> {

                    boolean selected = selectedUpgradeList.stream()
                            .anyMatch(selectedUpgrade ->
                                    selectedUpgrade.getUpgrade()
                                            .getId()
                                            .equals(upgrade.getId()));

                    return new UpgradeViewCombined(
                            upgrade.getId(),
                            upgrade.getName(),
                            upgrade.getPointsCost(),
                            upgrade.getUpgradeType(),
                            upgrade.getDescription(),
                            selected
                    );
                })
                .toList();
    }

    public void removeUpgrade(Long armyUnitId,Long upgradeId) {
        ArmyUnit armyUnit = armyUnitRepository.getReferenceById(armyUnitId);
        Long upgradeToDeleteId = null;
        for (SelectedUpgrade selectedUpgrade: armyUnit.getSelectedUpgradesList()) {
           if ( selectedUpgrade.getUpgrade().getId().equals(upgradeId)) {
               upgradeToDeleteId = selectedUpgrade.getId();
               break;
           }
        }
        if (upgradeToDeleteId == null) {
            throw new IllegalArgumentException("Upgrade with id " + upgradeId + " not found.");
        } else  {
            selectedUpgradeRepository.deleteById(upgradeToDeleteId);

        }
    }


}
