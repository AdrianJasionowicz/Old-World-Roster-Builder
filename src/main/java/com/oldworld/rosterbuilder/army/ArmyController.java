package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.army.model.*;
import com.oldworld.rosterbuilder.armyUnit.ArmyUnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/armies")
@RequiredArgsConstructor
class ArmyController {

    private final ArmyService armyService;
    private final ArmyPointsService armyPointsService;
    private final ArmyUnitService armyUnitService;

    @PostMapping
    ResponseEntity<CreateArmyResponse> createArmy(@Valid @RequestBody CreateArmyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(armyService.createArmy(request));
    }

    @GetMapping
    ResponseEntity<List<ArmyView>> getAllArmies() {
        return ResponseEntity.ok(armyService.getAllArmiesOfCurrentUser());
    }

    @GetMapping("/{armyId}")
    ResponseEntity<ArmyView> getArmyById(@PathVariable Long armyId) {
        return ResponseEntity.ok(armyService.getArmyById(armyId));
    }

    @DeleteMapping("/{armyId}")
    ResponseEntity<Void> deleteArmy(@PathVariable Long armyId) {
        armyService.deleteArmy(armyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{armyId}/units/{unitId}")
    ResponseEntity<ArmyView> addUnitToArmy(@PathVariable Long armyId, @PathVariable Long unitId) {
        return ResponseEntity.ok(armyUnitService.addArmyUnit(armyId, unitId));
    }

    @GetMapping("/{armyId}/points")
    ResponseEntity<ArmyPointsView> getArmyPoints(@PathVariable Long armyId) {
        return ResponseEntity.ok(armyPointsService.calculateArmyPoints(armyId));
    }

    @PatchMapping("/{armyId}/name")
    ResponseEntity<Void> editArmyName(@PathVariable Long armyId, @Valid @RequestBody RenameArmyRequest request) {
        armyService.editArmyName(armyId, request.name());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{armyId}/points-limit")
    ResponseEntity<Void> editArmyPointsLimit(@PathVariable Long armyId, @Valid @RequestBody ChangePointsLimitRequest request) {
        armyService.updateArmyPointsLimit(armyId, request.pointsLimit());
        return ResponseEntity.noContent().build();
    }
}
