package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.*;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.exception.ArmyNotFoundException;
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

    public void deleteArmy(Long armyId) {
        Army army = getArmyOwnedByCurrentUser(armyId);
        armyRepository.delete(army);
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

    public ArmyPointsView calculateArmyPoints(Long armyId) {
        Army army = getArmyOwnedByCurrentUser(armyId);
        double pointsLimit = army.getPointsLimit();
        double lordsAndHeroesAndSpecialLimit = pointsLimit * 0.5;
        double coreAndRare = pointsLimit * 0.25;
        double usedLords = 0.0;
        double usedCore = 0.0;
        double usedRare = 0.0;
        double usedSpecial = 0.0;
        double usedHeroes = 0.0;
        double totalSpendPoints = usedLords + usedCore + usedRare + usedSpecial + usedHeroes;

        for (ArmyUnit armyUnit : army.getArmyUnitsList()) {
            switch (armyUnit.getUnit().getUnitType()) {
                case CORE -> usedCore += armyUnit.getTotalCost();
                case RARE -> usedRare += armyUnit.getTotalCost();
                case SPECIAL -> usedSpecial += armyUnit.getTotalCost();
                case LORDS -> usedLords += armyUnit.getTotalCost();
                case HERO -> usedHeroes += armyUnit.getTotalCost();
            }
        }
        boolean coreValid = usedCore >= coreAndRare;
        CorePointsView corePointsView = new CorePointsView(usedCore, coreAndRare, coreValid);
        boolean specialValid = usedSpecial <= lordsAndHeroesAndSpecialLimit;
        SpecialPointsView specialPointsView = new SpecialPointsView(usedSpecial, lordsAndHeroesAndSpecialLimit, specialValid);
        boolean rareValid = usedRare <= coreAndRare;
        RarePointsView rarePointsView = new RarePointsView(usedRare, coreAndRare, rareValid);
        boolean lordsValid = usedLords <= lordsAndHeroesAndSpecialLimit;
        LordsPointsView lordsPointsView = new LordsPointsView(usedLords, lordsAndHeroesAndSpecialLimit, lordsValid);
        boolean heroesValid = usedHeroes <= lordsAndHeroesAndSpecialLimit;
        HeroesPointsView heroesPointsView = new HeroesPointsView(usedHeroes, lordsAndHeroesAndSpecialLimit, heroesValid);
        boolean arePointsValid = totalSpendPoints <= pointsLimit;

        return new ArmyPointsView(totalSpendPoints,pointsLimit,lordsPointsView,heroesPointsView,corePointsView,specialPointsView,rarePointsView,arePointsValid);
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

    private Army getArmyOwnedByCurrentUser(Long armyId) {
        Army army = getArmyEntityById(armyId);
        currentUserService.validateArmyAccess(army);
        return army;
    }


    private Army getArmyEntityById(Long armyId) {
        return armyRepository.findById(armyId).orElseThrow(() -> new ArmyNotFoundException("Army with id " + armyId + " not found"));
    }
}
