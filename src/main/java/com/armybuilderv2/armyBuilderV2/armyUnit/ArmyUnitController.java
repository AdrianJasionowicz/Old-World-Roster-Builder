package com.armybuilderv2.armyBuilderV2.armyUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/army-units/")
public class ArmyUnitController {

    private final ArmyUnitService armyUnitService;

    public ArmyUnitController(ArmyUnitService armyUnitService) {
        this.armyUnitService = armyUnitService;
    }

    @PostMapping("/{armyUnitId}/increase")
    public ResponseEntity<Void> increaseUnitSize(@PathVariable Long armyUnitId) {
        armyUnitService.changeUnitSize(armyUnitId,1);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{armyUnitId}/decrease")
    public ResponseEntity<?> decreaseUnitSize(@PathVariable Long armyUnitId) {
        armyUnitService.changeUnitSize(armyUnitId,-1);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{armyUnitId}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long armyUnitId) {
        armyUnitService.deleteArmyUnit(armyUnitId);
        return ResponseEntity.noContent().build();
    }
}