package com.armybuilderv2.armyBuilderV2.armyUnit;

import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitCannontBeDecreasedException;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgrade;
import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmyUnitService {

    private final ArmyUnitRepository armyUnitRepository;
    private final CurrentUserService currentUserService;

    public ArmyUnitService(ArmyUnitRepository armyUnitRepository, CurrentUserService currentUserService) {
        this.armyUnitRepository = armyUnitRepository;
        this.currentUserService = currentUserService;
    }

    @Transactional
    public void changeUnitSize(Long armyUnitId, int delta) {
        ArmyUnit armyUnit = getArmyUnitOwnedByCurrentUser(armyUnitId);

        armyUnit.setQuantity(armyUnit.getQuantity() + delta);
        adjustWeaponUpgrades(armyUnit);

        synchronizeQuantity(armyUnit);
        calculateArmyUnitCost(armyUnit);
    }

    private void calculateArmyUnitCost(ArmyUnit armyUnit) {
        double totalCost = 0.0;
        totalCost += armyUnit.getQuantity()*armyUnit.getUnit().getPointsCostPerUnit();

        List<SelectedUpgrade> selectedUpgradeList = armyUnit.getSelectedUpgradesList();
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
           totalCost += selectedUpgrade.getQuantity()*selectedUpgrade.getUpgrade().getPointsCost();
        }
        armyUnit.setTotalCost(totalCost);
    }

    protected void synchronizeQuantity(ArmyUnit armyUnit) {
        List<SelectedUpgrade> selectedUpgradeList = armyUnit.getSelectedUpgradesList();
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
            if (selectedUpgrade.getUpgrade().getUpgradeType().equals(UpgradeType.WEAPON)) {
                selectedUpgrade.setQuantity(armyUnit.getQuantity());
            }
        }
    }

    public void deleteArmyUnit(Long armyUnitId) {
        ArmyUnit armyUnit = getArmyUnitOwnedByCurrentUser(armyUnitId);
        armyUnitRepository.delete(armyUnit);
    }

    private void adjustWeaponUpgrades(ArmyUnit armyUnit) {
        if (armyUnit.getQuantity() < armyUnit.getUnit().getMinQuantity()) {
            throw new ArmyUnitCannontBeDecreasedException("Unit cannot be decreased");
        }
    }


    private ArmyUnit getArmyUnitOwnedByCurrentUser(Long id) {
        ArmyUnit armyUnit = armyUnitRepository.findById(id)
                .orElseThrow(() -> new ArmyUnitNotFoundException("Army unit not found with id: " + id));

        currentUserService.validateArmyAccess(armyUnit.getArmy());

        return armyUnit;
    }
}
