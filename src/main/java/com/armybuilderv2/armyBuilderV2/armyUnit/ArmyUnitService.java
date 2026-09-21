package com.armybuilderv2.armyBuilderV2.armyUnit;

import com.armybuilderv2.armyBuilderV2.army.*;
import com.armybuilderv2.armyBuilderV2.army.model.ArmyView;
import com.armybuilderv2.armyBuilderV2.exception.ArmyUnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedQuantityValidator;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgradeValidatorService;
import com.armybuilderv2.armyBuilderV2.unit.Unit;
import com.armybuilderv2.armyBuilderV2.unit.UnitFaction;
import com.armybuilderv2.armyBuilderV2.unit.UnitRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ArmyUnitService {

    private final ArmyUnitRepository armyUnitRepository;
    private final CurrentUserService currentUserService;
    private final SelectedUpgradeValidatorService selectedUpgradeValidatorService;
    private final SelectedQuantityValidator selectedQuantityValidator;
    private final UnitRepository unitRepository;
    private final ArmyRepository armyRepository;
    private final ArmyMapper armyMapper;

    public ArmyUnitService(ArmyUnitRepository armyUnitRepository, CurrentUserService currentUserService, SelectedUpgradeValidatorService selectedUpgradeValidatorService, SelectedQuantityValidator selectedQuantityValidator, ArmyService armyService, UnitRepository unitRepository, ArmyRepository armyRepository, ArmyMapper armyMapper) {
        this.armyUnitRepository = armyUnitRepository;
        this.currentUserService = currentUserService;
        this.selectedUpgradeValidatorService = selectedUpgradeValidatorService;
        this.selectedQuantityValidator = selectedQuantityValidator;
        this.unitRepository = unitRepository;
        this.armyRepository = armyRepository;
        this.armyMapper = armyMapper;
    }

    @Transactional
    public void changeUnitSize(Long armyUnitId, int delta) {
        ArmyUnit armyUnit = getArmyUnitOwnedByCurrentUser(armyUnitId);
        selectedQuantityValidator.checkIfUnitSizeCanBeChanged(armyUnit);
        armyUnit.setQuantity(armyUnit.getQuantity() + delta);
        selectedQuantityValidator.validateArmyUnitQuantity(armyUnit);
        selectedUpgradeValidatorService.checkAllUpgrades(armyUnit);
    }

    @Transactional
    public ArmyView addArmyUnit(Long armyId, Long unitId) {
        // Todo: Faction validation 
        Army army = currentUserService.getArmyOwnedByCurrentUser(armyId);
        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new IllegalArgumentException("Unit with id " + unitId + " not found"));
        validFaction(army.getFaction(),unit.getUnitFaction());
        ArmyUnit armyUnit = new ArmyUnit();
        armyUnit.setQuantity(unit.getMinQuantity());
        armyUnit.setUnit(unit);
        armyUnit.setSelectedUpgradesList(new ArrayList<>());
        armyUnit.setTotalCost(unit.getPointsCostPerUnit() * unit.getMinQuantity());
        armyUnit.setArmy(army);
        army.getArmyUnitsList().add(armyUnit);
        armyRepository.save(army);

        return armyMapper.makeView(army);
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

    void validFaction(Faction faction, UnitFaction unitFaction) {
        if (!faction.name().equals(unitFaction.name())) {
            throw new IllegalArgumentException("Factions do not match");
        }
    }
}