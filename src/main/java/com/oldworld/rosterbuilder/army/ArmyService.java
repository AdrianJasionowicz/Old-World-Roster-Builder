package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.army.model.ArmyView;
import com.oldworld.rosterbuilder.army.model.CreateArmyRequest;
import com.oldworld.rosterbuilder.army.model.CreateArmyResponse;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class ArmyService {

    private final ArmyRepository armyRepository;
    private final ArmyMapper armyMapper;
    private final CurrentUserService currentUserService;

    @Transactional
    public CreateArmyResponse createArmy(CreateArmyRequest request) {
        var owner = currentUserService.getCurrentUser();
        var army = new Army();
        army.setName(request.name());
        army.setDescription(request.description());
        army.setPointsLimit(request.pointsLimit());
        army.setFaction(request.faction());
        army.setOwner(owner);
        armyRepository.save(army);
        log.info("User '{}' created army {} ({}, {} pts)", owner.getUsername(), army.getId(), army.getFaction(), army.getPointsLimit());
        return new CreateArmyResponse(army.getId(), army.getName(), army.getDescription(), army.getPointsLimit(), army.getFaction());
    }

    public List<ArmyView> getAllArmiesOfCurrentUser() {
        return armyRepository.findByOwner(currentUserService.getCurrentUser())
                .stream()
                .map(armyMapper::makeView)
                .toList();
    }

    public ArmyView getArmyById(Long armyId) {
        return armyMapper.makeView(currentUserService.getArmyOwnedByCurrentUser(armyId));
    }

    @Transactional
    public void deleteArmy(Long armyId) {
        var army = currentUserService.getArmyOwnedByCurrentUser(armyId);
        armyRepository.delete(army);
        log.info("Deleted army {}", armyId);
    }

    @Transactional
    public void editArmyName(Long armyId, String newName) {
        var army = currentUserService.getArmyOwnedByCurrentUser(armyId);
        army.setName(newName);
        log.info("Renamed army {} to '{}'", armyId, newName);
    }

    @Transactional
    public void updateArmyPointsLimit(Long armyId, Double newPointsLimit) {
        var army = currentUserService.getArmyOwnedByCurrentUser(armyId);
        army.setPointsLimit(newPointsLimit);
        log.info("Changed points limit of army {} to {}", armyId, newPointsLimit);
    }
}
