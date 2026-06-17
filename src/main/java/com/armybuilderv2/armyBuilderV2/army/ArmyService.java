package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.ArmyView;
import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyRequest;
import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyResponse;
import com.armybuilderv2.armyBuilderV2.exception.ArmyNotFoundException;
import com.armybuilderv2.armyBuilderV2.exception.NoCreateArmyRequestException;
import com.armybuilderv2.armyBuilderV2.loginUser.LoginUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmyService {
    private final ArmyRepository armyRepository;
    private final ArmyMapper armyMapper;

    public ArmyService(ArmyRepository armyRepository, ArmyMapper armyMapper) {
        this.armyRepository = armyRepository;
        this.armyMapper = armyMapper;
    }


    public CreateArmyResponse createArmy(CreateArmyRequest request) {
        if (request == null) {
            throw new NoCreateArmyRequestException("Army Request cannot be null");
        }

        Army army = new Army();
        army.setDescription(request.description());
        army.setName(request.name());
        army.setPointsLimit(request.pointsLimit());
        army.setFaction(request.faction());

        armyRepository.save(army);


        return new CreateArmyResponse(army.getId(), army.getName(), army.getDescription(), army.getPointsLimit(), army.getFaction());
    }


    public List<ArmyView> getAllArmiesByUsername() {
        LoginUser loginUser = null; // Add later Security -> get username from cookie

        return armyRepository.findByOwner(loginUser)
                .stream()
                .map(armyMapper::makeView)
                .toList();
    }

    public void deleteArmy(Long id) {
        armyRepository.findById(id).orElseThrow(() -> new ArmyNotFoundException("Army not found"));

        armyRepository.deleteById(id);
    }



    public ArmyView getArmyById(Long id) {
        Army army = armyRepository.findById(id).orElseThrow(() ->
                new ArmyNotFoundException("Army with id " + id + " not found"));
        ;

        return armyMapper.makeView(army);
    }

}
