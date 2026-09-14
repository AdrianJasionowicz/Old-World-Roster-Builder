package com.armybuilderv2.armyBuilderV2.armyUnit;

import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedQuantityValidator;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgradeValidatorService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ArmyUnitService {

    private final ArmyUnitRepository armyUnitRepository;
    private final CurrentUserService currentUserService;
    private final SelectedUpgradeValidatorService selectedUpgradeValidatorService;
    private final SelectedQuantityValidator selectedQuantityValidator;

    public ArmyUnitService(ArmyUnitRepository armyUnitRepository, CurrentUserService currentUserService, SelectedUpgradeValidatorService selectedUpgradeValidatorService, SelectedQuantityValidator selectedQuantityValidator) {
        this.armyUnitRepository = armyUnitRepository;
        this.currentUserService = currentUserService;
        this.selectedUpgradeValidatorService = selectedUpgradeValidatorService;
        this.selectedQuantityValidator = selectedQuantityValidator;
    }

    @Transactional
    public void changeUnitSize(Long armyUnitId, int delta) {
        ArmyUnit armyUnit = getArmyUnitOwnedByCurrentUser(armyUnitId);
        selectedQuantityValidator.checkIfUnitSizeCanBeChanged(armyUnit);
        armyUnit.setQuantity(armyUnit.getQuantity() + delta);
        selectedQuantityValidator.validateArmyUnitQuantity(armyUnit);
        selectedUpgradeValidatorService.checkAllUpgrades(armyUnit);
    }





    public void deleteArmyUnit(Long armyUnitId) {
        ArmyUnit armyUnit = getArmyUnitOwnedByCurrentUser(armyUnitId);
        armyUnitRepository.delete(armyUnit);
    }


    ArmyUnit getArmyUnitOwnedByCurrentUser(Long id) {
        ArmyUnit armyUnit = armyUnitRepository.findById(id)
                .orElseThrow(() -> new ArmyUnitNotFoundException("Army unit not found with id: " + id));

        currentUserService.validateArmyAccess(armyUnit.getArmy());

        return armyUnit;
    }


}
