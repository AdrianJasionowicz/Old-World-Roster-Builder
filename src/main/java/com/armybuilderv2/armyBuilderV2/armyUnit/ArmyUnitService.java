package com.armybuilderv2.armyBuilderV2.armyUnit;

import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitCannontBeDecreasedException;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.loginUser.LoginUserRepository;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgrade;
import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmyUnitService {

    private final ArmyUnitRepository armyUnitRepository;
    private final LoginUserRepository loginUserRepository;


    public ArmyUnitService(ArmyUnitRepository armyUnitRepository, LoginUserRepository loginUserRepository) {
        this.armyUnitRepository = armyUnitRepository;
        this.loginUserRepository = loginUserRepository;
    }

    @Transactional
    public void changeUnitSize(Long armyUnitId, int delta) {
        ArmyUnit armyUnit = getArmyUnit(armyUnitId);

        armyUnit.setQuantity(armyUnit.getQuantity() + delta);
        adjustWeaponUpgrades(armyUnit);

        validateQuantity(armyUnit);
        calculateArmyUnitCost(armyUnit);
    }

    public void calculateArmyUnitCost(ArmyUnit armyUnit) {
        double totalCost = 0.0;
        totalCost += armyUnit.getQuantity()*armyUnit.getUnit().getPointsCostPerUnit();

        List<SelectedUpgrade> selectedUpgradeList = armyUnit.getSelectedUpgradesList();
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
           totalCost += selectedUpgrade.getQuantity()*selectedUpgrade.getUpgrade().getPointsCost();
        }
        armyUnit.setTotalCost(totalCost);
    }

    public void deleteArmyUnit(Long armyUnitId) {
        if (armyUnitRepository.existsArmyUnitById(armyUnitId)) {
            armyUnitRepository.deleteById(armyUnitId);
        }
    }

    private ArmyUnit getArmyUnit(Long armyUnitId) {
        return armyUnitRepository.findById(armyUnitId).orElseThrow(() -> new ArmyUnitNotFoundException("Army unit not found with this id: " + armyUnitId));
    }

    private void adjustWeaponUpgrades(ArmyUnit armyUnit) {
        if (armyUnit.getQuantity() < armyUnit.getUnit().getMinQuantity()) {
            throw new ArmyUnitCannontBeDecreasedException("Unit cannot be decreased");
        }
    }

    protected void validateQuantity(ArmyUnit armyUnit) {
        List<SelectedUpgrade> selectedUpgradeList = armyUnit.getSelectedUpgradesList();
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
            if (selectedUpgrade.getUpgrade().getUpgradeType().equals(UpgradeType.WEAPON)) {
                selectedUpgrade.setQuantity(armyUnit.getQuantity());
            }
        }
        armyUnit.setSelectedUpgradesList(selectedUpgradeList);

    }
}
