package com.armybuilderv2.armyBuilderV2.armyUnit;

import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitCannontBeDecreasedException;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitSizeCannotBeChangedException;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgrade;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgradeValidatorService;
import com.armybuilderv2.armyBuilderV2.unit.UnitType;
import com.armybuilderv2.armyBuilderV2.upgrade.UpgradeType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmyUnitService {

    private final ArmyUnitRepository armyUnitRepository;
    private final CurrentUserService currentUserService;
    private SelectedUpgradeValidatorService selectedUpgradeValidatorService;

    public ArmyUnitService(ArmyUnitRepository armyUnitRepository, CurrentUserService currentUserService, SelectedUpgradeValidatorService selectedUpgradeValidatorService) {
        this.armyUnitRepository = armyUnitRepository;
        this.currentUserService = currentUserService;
        this.selectedUpgradeValidatorService = selectedUpgradeValidatorService;
    }

    @Transactional
    public void changeUnitSize(Long armyUnitId, int delta) {
        ArmyUnit armyUnit = getArmyUnitOwnedByCurrentUser(armyUnitId);
        checkIfUnitSizeCanBeChanged(armyUnit);
        armyUnit.setQuantity(armyUnit.getQuantity() + delta);
        selectedUpgradeValidatorService.checkAllUpgrades(armyUnit);
        checkUnitSize(armyUnit);
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

    private void synchronizeQuantity(ArmyUnit armyUnit) {
        List<SelectedUpgrade> selectedUpgradeList = armyUnit.getSelectedUpgradesList();
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
            if (selectedUpgrade.getUpgrade().getUpgradeType().equals(UpgradeType.UNIT_EQUIPMENT)) {
                selectedUpgrade.setQuantity(armyUnit.getQuantity());
            }
        }
    }

    public void deleteArmyUnit(Long armyUnitId) {
        ArmyUnit armyUnit = getArmyUnitOwnedByCurrentUser(armyUnitId);
        armyUnitRepository.delete(armyUnit);
    }

    private void checkUnitSize(ArmyUnit armyUnit) {
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

    private void checkIfUnitSizeCanBeChanged(ArmyUnit armyUnit) {
        UnitType unitType = armyUnit.getUnit().getUnitType();

        if (unitType == UnitType.HERO
                || unitType == UnitType.LORDS
                || unitType == UnitType.RARE) {
            throw new ArmyUnitSizeCannotBeChangedException(
                    "Unit size cannot be changed for " + unitType + " units"
            );
        }
    }
}
