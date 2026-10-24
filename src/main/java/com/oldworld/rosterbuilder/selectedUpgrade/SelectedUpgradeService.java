package com.oldworld.rosterbuilder.selectedUpgrade;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.exception.ConflictException;
import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.selectedUpgrade.model.UpgradeViewCombined;
import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class SelectedUpgradeService {

    private final CurrentUserService currentUserService;
    private final SelectedUpgradeValidatorService selectedUpgradeValidatorService;
    private final SelectedQuantityValidator selectedQuantityValidator;

    @Transactional
    public void selectUpgrade(Long armyUnitId, Long unitUpgradeId) {
        var armyUnit = currentUserService.getArmyUnitOwnedByCurrentUser(armyUnitId);
        if (isSelected(armyUnit, unitUpgradeId)) {
            throw new ConflictException("Upgrade already selected.");
        }
        var candidate = findAvailableUpgrade(armyUnit, unitUpgradeId);
        selectedUpgradeValidatorService.validateSelection(armyUnit, candidate);

        armyUnit.getSelectedUpgradesList().add(new SelectedUpgrade(armyUnit, candidate));
        selectedQuantityValidator.recalculateQuantityAndCost(armyUnit);
        log.info("Army unit {} selected upgrade '{}', unit cost is now {}", armyUnitId, candidate.getUpgrade().getName(), armyUnit.getTotalCost());
    }

    public List<UpgradeViewCombined> getUpgradeView(Long armyUnitId) {
        var armyUnit = currentUserService.getArmyUnitOwnedByCurrentUser(armyUnitId);
        return armyUnit.getUnit().getUnitUpgrades().stream()
                .map(unitUpgrade -> {
                    var selected = isSelected(armyUnit, unitUpgrade.getId());
                    var notAllowedReason = selected
                            ? null
                            : selectedUpgradeValidatorService.findSelectionViolation(armyUnit, unitUpgrade).orElse(null);
                    return UpgradeViewCombined.from(unitUpgrade, selected, notAllowedReason);
                })
                .toList();
    }

    @Transactional
    public void removeUpgrade(Long armyUnitId, Long unitUpgradeId) {
        var armyUnit = currentUserService.getArmyUnitOwnedByCurrentUser(armyUnitId);
        var selectedUpgrade = armyUnit.getSelectedUpgradesList().stream()
                .filter(su -> su.isFor(unitUpgradeId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Upgrade not found with id: " + unitUpgradeId));
        armyUnit.getSelectedUpgradesList().remove(selectedUpgrade);
        selectedQuantityValidator.recalculateQuantityAndCost(armyUnit);
        log.info("Army unit {} removed upgrade '{}', unit cost is now {}", armyUnitId, selectedUpgrade.getUpgrade().getName(), armyUnit.getTotalCost());
    }

    private boolean isSelected(ArmyUnit armyUnit, Long unitUpgradeId) {
        return armyUnit.getSelectedUpgradesList().stream().anyMatch(su -> su.isFor(unitUpgradeId));
    }

    private UnitUpgrade findAvailableUpgrade(ArmyUnit armyUnit, Long unitUpgradeId) {
        return armyUnit.getUnit().getUnitUpgrades().stream()
                .filter(unitUpgrade -> unitUpgrade.getId().equals(unitUpgradeId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Upgrade not found with id: " + unitUpgradeId));
    }
}
