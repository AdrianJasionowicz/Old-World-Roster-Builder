package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/armies")
public class ArmyController {

    private final ArmyService armyService;


    public ArmyController(ArmyService armyService) {
        this.armyService = armyService;
    }

    @PostMapping
    public ResponseEntity<?> createArmy(@RequestBody CreateArmyRequest request) {
        return ResponseEntity.ok(armyService.createArmy(request));
    }


    @GetMapping
    public ResponseEntity<?> getAllArmies() {

        return ResponseEntity.ok(armyService.getAllArmiesByUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArmyById(@PathVariable Long id) {
        armyService.deleteArmy(id);
      return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArmyById(@PathVariable Long id) {
        return ResponseEntity.ok(armyService.getArmyById(id));
    }


    @PostMapping("/{armyId}/units/{unitId}")
    public ResponseEntity<?> addUnitToArmy(@PathVariable Long armyId, @PathVariable Long unitId) {
        return ResponseEntity.ok(armyService.addArmyUnit(armyId, unitId));
    }

    @GetMapping("/{armyId}/points")
    public ResponseEntity<?> getArmyUnitPoints(@PathVariable Long armyId) {
        return ResponseEntity.ok(armyService.calculateArmyPoints(armyId));
    }

    @PostMapping("/{armyId}/changeName")
    public ResponseEntity<?> editArmyName(@PathVariable Long armyId, @RequestParam String newName) {
        armyService.editArmyName(armyId,newName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{armyId}/updatePoints")
    public ResponseEntity<?> editArmyPointsLimit(@PathVariable Long armyId, @RequestParam Double newPointsLimit) {
        armyService.updateArmyPointsLimit(armyId,newPointsLimit);
        return ResponseEntity.ok().build();

    }



}
