package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.*;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.exception.ArmyNotFoundException;
import com.armybuilderv2.armyBuilderV2.exception.NoCreateArmyRequestException;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.unit.Unit;
import com.armybuilderv2.armyBuilderV2.unit.UnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArmyService {
    private final ArmyRepository armyRepository;
    private final ArmyMapper armyMapper;
    private final UnitRepository unitRepository;
    private final CurrentUserService currentUserService;

    public ArmyService(ArmyRepository armyRepository, ArmyMapper armyMapper, UnitRepository unitRepository, CurrentUserService currentUserService) {
        this.armyRepository = armyRepository;
        this.armyMapper = armyMapper;
        this.unitRepository = unitRepository;
        this.currentUserService = currentUserService;
    }

    @Transactional
    public CreateArmyResponse createArmy(CreateArmyRequest request) {
        if (request == null) {
            throw new NoCreateArmyRequestException("The request form is invalid");
        }
        Army army = new Army();
        army.setDescription(request.description());
        army.setName(request.name());
        army.setPointsLimit(request.pointsLimit());
        army.setFaction(request.faction());
        army.setOwner(currentUserService.getCurrentUser());
        armyRepository.save(army);
        return new CreateArmyResponse(army.getId(), army.getName(), army.getDescription(), army.getPointsLimit(), army.getFaction());
    }


    public List<ArmyView> getAllArmiesByUsername() {
        return armyRepository.findByOwner(currentUserService.getCurrentUser())
                .stream()
                .map(armyMapper::makeView)
                .toList();
    }

    @Transactional
    public void deleteArmy(Long armyId) {
        Army army = getArmyOwnedByCurrentUser(armyId);
        armyRepository.forceDelete(armyId);
    }


    public ArmyView getArmyById(Long armyId) {
        Army army = getArmyOwnedByCurrentUser(armyId);
        return armyMapper.makeView(army);

    }

    @Transactional
    public ArmyView addArmyUnit(Long armyId, Long unitId) {
        Army army = getArmyOwnedByCurrentUser(armyId);
        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new IllegalArgumentException("Unit with id " + unitId + " not found"));
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


    @Transactional
    public void editArmyName(Long armyId, String newName) {
        Army army = getArmyOwnedByCurrentUser(armyId);
        army.setName(newName);
    }

    @Transactional
    public void updateArmyPointsLimit(Long armyId, Double newPointsLimit) {
        Army army = getArmyOwnedByCurrentUser(armyId);
        army.setPointsLimit(newPointsLimit);
    }

    Army getArmyOwnedByCurrentUser(Long armyId) {
        Army army = getArmyEntityById(armyId);
        currentUserService.validateArmyAccess(army);
        return army;
    }


    private Army getArmyEntityById(Long armyId) {
        return armyRepository.findById(armyId).orElseThrow(() -> new ArmyNotFoundException("Army with id " + armyId + " not found"));
    }
}
