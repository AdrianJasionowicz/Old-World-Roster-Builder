package com.armybuilderv2.armyBuilderV2.unit;

import com.armybuilderv2.armyBuilderV2.unit.model.UnitRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<?> addUnit(@RequestBody UnitRequest unitRequest) {

        return ResponseEntity.ok(unitService.addUnit(unitRequest));
    }

    @GetMapping("/faction")
    public ResponseEntity<?> getAllUnitsByFaction(@RequestParam String faction) {
        return ResponseEntity.ok(unitService.getAllByFaction(faction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUnitById(@PathVariable Long id) {
        unitService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
