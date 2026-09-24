package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.ArmyView;
import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyRequest;
import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyResponse;
import com.armybuilderv2.armyBuilderV2.exception.NoCreateArmyRequestException;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArmyService {
    private final ArmyRepository armyRepository;
    private final ArmyMapper armyMapper;
    private final CurrentUserService currentUserService;

    public ArmyService(ArmyRepository armyRepository, ArmyMapper armyMapper, CurrentUserService currentUserService) {
        this.armyRepository = armyRepository;
        this.armyMapper = armyMapper;
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
        Army army = currentUserService.getArmyOwnedByCurrentUser(armyId);
        army.getArmyUnitsList().clear();
        armyRepository.save(army);
        armyRepository.forceDelete(armyId);
    }

    public ArmyView getArmyById(Long armyId) {
        Army army = currentUserService.getArmyOwnedByCurrentUser(armyId);
        return armyMapper.makeView(army);
    }

    @Transactional
    public void editArmyName(Long armyId, String newName) {
        Army army = currentUserService.getArmyOwnedByCurrentUser(armyId);
        army.setName(newName);
    }

    @Transactional
    public void updateArmyPointsLimit(Long armyId, Double newPointsLimit) {
        Army army = currentUserService.getArmyOwnedByCurrentUser(armyId);
        army.setPointsLimit(newPointsLimit);
    }
}