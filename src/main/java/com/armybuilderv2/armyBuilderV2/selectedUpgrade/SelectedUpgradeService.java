package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnitRepository;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.exception.UpgradeAlreadySelectedException;
import com.armybuilderv2.armyBuilderV2.exception.UpgradeNotFoundException;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.model.UpgradeViewCombined;
import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SelectedUpgradeService {

    private final SelectedUpgradeRepository selectedUpgradeRepository;
    private final ArmyUnitRepository armyUnitRepository;
    private SelectedUpgradeMapper selectedUpgradeMapper;
    private final CurrentUserService currentUserService;
    private final SelectedUpgradeValidatorService selectedUpgradeValidatorService;

    public SelectedUpgradeService(SelectedUpgradeRepository selectedUpgradeRepository, ArmyUnitRepository armyUnitRepository, SelectedUpgradeMapper selectedUpgradeMapper, CurrentUserService currentUserService, SelectedUpgradeValidatorService selectedUpgradeValidatorService) {
        this.selectedUpgradeRepository = selectedUpgradeRepository;
        this.armyUnitRepository = armyUnitRepository;
        this.selectedUpgradeMapper = selectedUpgradeMapper;
        this.currentUserService = currentUserService;
        this.selectedUpgradeValidatorService = selectedUpgradeValidatorService;
    }

    @Transactional
    public void selectUpgrade(Long armyUnitId, Long upgradeId) {
        ArmyUnit armyUnit = getArmyUnitOwnedByCurrentUser(armyUnitId);


        boolean alreadySelected = armyUnit.getSelectedUpgradesList()
                .stream()
                .anyMatch(su ->
                        su.getUpgrade().getId().equals(upgradeId));

        if (alreadySelected) {
            throw new UpgradeAlreadySelectedException("Upgrade already selected.");
        }

        List<Upgrade> upgradeList = armyUnit.getUnit().getUpgradesList();

        Upgrade upgradeToSelect = upgradeList
                .stream()
                .filter(upgrade -> upgrade.getId().equals(upgradeId))
                .findFirst()
                .orElseThrow(()-> new UpgradeNotFoundException("Upgrade not found with id: " + upgradeId));

        SelectedUpgrade selectedUpgrade = selectedUpgradeMapper.mapUpgradeToSelectedUpgrade(upgradeToSelect);
        selectedUpgrade.setArmyUnit(armyUnit);
        armyUnit.getSelectedUpgradesList().add(selectedUpgrade);

        selectedUpgradeValidatorService.checkUpgradeQuantities(armyUnit,upgradeId);
        selectedUpgradeValidatorService.validateWeaponTeams(armyUnit);
        selectedUpgradeValidatorService.validateBattleStandards(armyUnit);
        selectedUpgradeValidatorService.checkLordsAndHeroUpgrades(armyUnit);
        if (selectedUpgradeValidatorService.validateMagicBannerAndCheckPresence(armyUnit)) {
            selectedUpgradeValidatorService.validateMagicBannerRestrictions(armyUnit);
        }


    }



    public List<UpgradeViewCombined> getUpgradeView(Long armyUnitId) {
        ArmyUnit armyUnit = getArmyUnitOwnedByCurrentUser(armyUnitId);

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
    @Transactional
    public void removeUpgrade(Long armyUnitId,Long upgradeId) {
        ArmyUnit armyUnit = getArmyUnitOwnedByCurrentUser(armyUnitId);

        SelectedUpgrade selectedUpgrade = armyUnit.getSelectedUpgradesList().stream()
                .filter(su -> su.getUpgrade().getId().equals(upgradeId))
                .findFirst()
                .orElseThrow(()-> new UpgradeNotFoundException("Upgrade not found with id: " + upgradeId));
        selectedUpgradeRepository.forceDelete(selectedUpgrade.getId());

    }

    private ArmyUnit getArmyUnitOwnedByCurrentUser(Long armyUnitId) {
        ArmyUnit armyUnit = armyUnitRepository.findById(armyUnitId)
                .orElseThrow(() -> new ArmyUnitNotFoundException("Army unit not found with id: " + armyUnitId));

        currentUserService.validateArmyAccess(armyUnit.getArmy());

        return armyUnit;
    }

}
