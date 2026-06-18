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
    public ResponseEntity<?> increaseUnitSize(@PathVariable Long armyUnitId) {
        armyUnitService.increaseUnitSize(armyUnitId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{armyUnitId}/decrease")
    public ResponseEntity<?> decreaseUnitSize(@PathVariable Long armyUnitId) {
        armyUnitService.decreaseUnitSize(armyUnitId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{armyUnitId}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long armyUnitId) {
        armyUnitService.deleteArmyUnit(armyUnitId);
        return ResponseEntity.ok().build();
    }

}
