package com.oldworld.rosterbuilder.armyUnit;

import com.oldworld.rosterbuilder.army.model.ArmyView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/army-units")
@RequiredArgsConstructor
class ArmyUnitController {

    private final ArmyUnitService armyUnitService;

    @PostMapping("/{armyUnitId}/increase")
    ResponseEntity<Void> increaseUnitSize(@PathVariable Long armyUnitId) {
        armyUnitService.changeUnitSize(armyUnitId, 1);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{armyUnitId}/decrease")
    ResponseEntity<Void> decreaseUnitSize(@PathVariable Long armyUnitId) {
        armyUnitService.changeUnitSize(armyUnitId, -1);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{armyUnitId}/detachments/{unitId}")
    ResponseEntity<ArmyView> addDetachment(@PathVariable Long armyUnitId, @PathVariable Long unitId) {
        return ResponseEntity.ok(armyUnitService.addDetachment(armyUnitId, unitId));
    }

    @DeleteMapping("/{armyUnitId}")
    ResponseEntity<Void> deleteUnit(@PathVariable Long armyUnitId) {
        armyUnitService.deleteArmyUnit(armyUnitId);
        return ResponseEntity.noContent().build();
    }
}
