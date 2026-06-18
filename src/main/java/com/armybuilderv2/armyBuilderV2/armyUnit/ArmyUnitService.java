package com.armybuilderv2.armyBuilderV2.armyUnit;

import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgrade;
import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmyUnitService {

    private final ArmyUnitRepository armyUnitRepository;


    public ArmyUnitService(ArmyUnitRepository armyUnitRepository) {
        this.armyUnitRepository = armyUnitRepository;
    }


    public void increaseUnitSize(Long armyUnitId) {
        ArmyUnit armyUnit = armyUnitRepository.findById(armyUnitId).orElseThrow(()->new IllegalArgumentException("Army unit not found"));
        armyUnit.setQuantity(armyUnit.getQuantity() + 1);

        List<SelectedUpgrade> selectedUpgradeList =  armyUnit.getSelectedUpgradesList();
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
           if ( selectedUpgrade.getUpgrade().getUpgradeType().equals(UpgradeType.WEAPON)) {
               selectedUpgrade.setQuantity(selectedUpgrade.getQuantity());
           }
        }
        armyUnit.setSelectedUpgradesList(selectedUpgradeList);
        armyUnitRepository.save(armyUnit);
        calculateArmyUnitCost(armyUnitId);

    }

    public void decreaseUnitSize(Long armyUnitId) {
        ArmyUnit armyUnit = armyUnitRepository.findById(armyUnitId).orElseThrow(()->new IllegalArgumentException("Army unit not found"));
        if (armyUnit.getQuantity() >= armyUnit.getUnit().getMinQuantity()) {
            armyUnit.setQuantity(armyUnit.getQuantity() - 1);
            List<SelectedUpgrade> selectedUpgradeList =  armyUnit.getSelectedUpgradesList();
            for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
                if ( selectedUpgrade.getUpgrade().getUpgradeType().equals(UpgradeType.WEAPON)) {
                    selectedUpgrade.setQuantity(selectedUpgrade.getQuantity());
                }
            }
            armyUnit.setSelectedUpgradesList(selectedUpgradeList);
            armyUnitRepository.save(armyUnit);
            calculateArmyUnitCost(armyUnitId);

        } else {
            throw new IllegalArgumentException("Unit cannot be decreased");
        }
    }

    public void calculateArmyUnitCost(Long armyUnitId) {
        ArmyUnit armyUnit = armyUnitRepository.findById(armyUnitId).orElseThrow(()->new IllegalArgumentException("Army unit not found"));
        double totalCost = 0.0;
        totalCost += armyUnit.getQuantity()*armyUnit.getUnit().getPointsCostPerUnit();

        List<SelectedUpgrade> selectedUpgradeList = armyUnit.getSelectedUpgradesList();
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
           totalCost += selectedUpgrade.getQuantity()*selectedUpgrade.getUpgrade().getPointsCost();
        }
        armyUnit.setTotalCost(totalCost);
        armyUnitRepository.save(armyUnit);
    }

    public void deleteArmyUnit(Long armyUnitId) {
        boolean exist = armyUnitRepository.existsArmyUnitById(armyUnitId);
        if (exist) {
            armyUnitRepository.deleteById(armyUnitId);
        }
    }
}
