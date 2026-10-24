package com.oldworld.rosterbuilder.armyUnit;

import com.oldworld.rosterbuilder.army.Army;
import com.oldworld.rosterbuilder.army.ArmyMapper;
import com.oldworld.rosterbuilder.army.model.ArmyView;
import com.oldworld.rosterbuilder.exception.BusinessRuleException;
import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedQuantityValidator;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedUpgrade;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unit.UnitRepository;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.oldworld.rosterbuilder.armyUnit.ArmyUnit.DETACHMENT_MIN_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArmyUnitService {

    private final ArmyUnitRepository armyUnitRepository;
    private final CurrentUserService currentUserService;
    private final SelectedQuantityValidator selectedQuantityValidator;
    private final DetachmentValidator detachmentValidator;
    private final UnitRepository unitRepository;
    private final ArmyMapper armyMapper;

    @Transactional
    public ArmyView addArmyUnit(Long armyId, Long unitId) {
        var army = currentUserService.getArmyOwnedByCurrentUser(armyId);
        var unit = findUnitOfFaction(army, unitId);
        var armyUnit = createArmyUnit(army, unit, unit.getMinQuantity(), null);
        log.info("Added '{}' x{} ({} pts) to army {}", unit.getName(), armyUnit.getQuantity(), armyUnit.getTotalCost(), armyId);
        return armyMapper.makeView(army);
    }

    @Transactional
    ArmyView addDetachment(Long parentId, Long unitId) {
        var parent = currentUserService.getArmyUnitOwnedByCurrentUser(parentId);
        var army = parent.getArmy();
        var unit = findUnitOfFaction(army, unitId);
        detachmentValidator.validateNewDetachment(parent, unit);

        var detachment = createArmyUnit(army, unit, DETACHMENT_MIN_SIZE, parent);
        log.info("Added detachment '{}' x{} to army unit {}", unit.getName(), detachment.getQuantity(), parentId);
        return armyMapper.makeView(army);
    }

    @Transactional
    void changeUnitSize(Long armyUnitId, int delta) {
        var armyUnit = currentUserService.getArmyUnitOwnedByCurrentUser(armyUnitId);
        selectedQuantityValidator.checkIfUnitSizeCanBeChanged(armyUnit);
        armyUnit.setQuantity(armyUnit.getQuantity() + delta);
        selectedQuantityValidator.validateArmyUnitQuantity(armyUnit);
        detachmentValidator.validateSize(armyUnit);
        log.info("Army unit {} resized to {} models ({} pts)", armyUnitId, armyUnit.getQuantity(), armyUnit.getTotalCost());
    }

    @Transactional
    void deleteArmyUnit(Long armyUnitId) {
        var armyUnit = currentUserService.getArmyUnitOwnedByCurrentUser(armyUnitId);
        var armyUnits = armyUnit.getArmy().getArmyUnitsList();
        armyUnits.removeAll(armyUnit.getDetachments());
        armyUnits.remove(armyUnit);
        if (armyUnit.isDetachment()) {
            armyUnit.getParent().getDetachments().remove(armyUnit);
        }
        log.info("Removed army unit {} with {} detachments from army {}", armyUnitId, armyUnit.getDetachments().size(), armyUnit.getArmy().getId());
    }

    private Unit findUnitOfFaction(Army army, Long unitId) {
        var unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new NotFoundException("Unit with id " + unitId + " not found"));
        if (!army.getFaction().name().equals(unit.getUnitFaction().name())) {
            throw new BusinessRuleException("Factions do not match");
        }
        return unit;
    }

    private ArmyUnit createArmyUnit(Army army, Unit unit, int quantity, ArmyUnit parent) {
        var armyUnit = new ArmyUnit();
        armyUnit.setQuantity(quantity);
        armyUnit.setUnit(unit);
        armyUnit.setArmy(army);
        armyUnit.setParent(parent);
        armyUnit.setSelectedUpgradesList(freeUpgradesOf(unit, armyUnit));
        selectedQuantityValidator.recalculateQuantityAndCost(armyUnit);
        armyUnitRepository.save(armyUnit);
        army.getArmyUnitsList().add(armyUnit);
        if (parent != null) {
            parent.getDetachments().add(armyUnit);
        }
        return armyUnit;
    }

    private List<SelectedUpgrade> freeUpgradesOf(Unit unit, ArmyUnit armyUnit) {
        return unit.getUnitUpgrades().stream()
                .filter(unitUpgrade -> unitUpgrade.getUpgrade().getUpgradeType() == UpgradeType.FREE_UPGRADE)
                .map(unitUpgrade -> new SelectedUpgrade(armyUnit, unitUpgrade))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
