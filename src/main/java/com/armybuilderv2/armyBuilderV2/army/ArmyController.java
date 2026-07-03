package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.ArmyPointsView;
import com.armybuilderv2.armyBuilderV2.army.model.ArmyView;
import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyRequest;
import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/armies")
public class ArmyController {

    private final ArmyService armyService;


    public ArmyController(ArmyService armyService) {
        this.armyService = armyService;
    }

    @PostMapping
    public ResponseEntity<CreateArmyResponse> createArmy(@Valid @RequestBody CreateArmyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(armyService.createArmy(request));
    }


    @GetMapping
    public ResponseEntity<List<ArmyView>> getAllArmies() {

        return ResponseEntity.ok(armyService.getAllArmiesByUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArmyById(@PathVariable Long id) {
        armyService.deleteArmy(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArmyView> getArmyById(@PathVariable Long id) {
        return ResponseEntity.ok(armyService.getArmyById(id));
    }


    @PostMapping("/{armyId}/units/{unitId}")
    public ResponseEntity<ArmyView> addUnitToArmy(@PathVariable Long armyId, @PathVariable Long unitId) {
        return ResponseEntity.ok(armyService.addArmyUnit(armyId, unitId));
    }

    @GetMapping("/{armyId}/points")
    public ResponseEntity<ArmyPointsView> getArmyUnitPoints(@PathVariable Long armyId) {
        return ResponseEntity.ok(armyService.calculateArmyPoints(armyId));
    }

    @PatchMapping("/{armyId}/name")
    public ResponseEntity<?> editArmyName(@PathVariable Long armyId, @RequestParam String newName) {
        armyService.editArmyName(armyId,newName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{armyId}/pointsLimit")
    public ResponseEntity<?> editArmyPointsLimit(@PathVariable Long armyId, @RequestParam Double newPointsLimit) {
        armyService.updateArmyPointsLimit(armyId,newPointsLimit);
        return ResponseEntity.ok().build();

    }



}
