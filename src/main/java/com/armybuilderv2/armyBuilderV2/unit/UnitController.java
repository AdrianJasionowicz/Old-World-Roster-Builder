package com.armybuilderv2.armyBuilderV2.unit;

import com.armybuilderv2.armyBuilderV2.unit.model.UnitRequest;
import com.armybuilderv2.armyBuilderV2.unit.model.UnitResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<UnitResponse> addUnit(@Valid  @RequestBody UnitRequest unitRequest) {
        return ResponseEntity.ok(unitService.addUnit(unitRequest));
    }

    @GetMapping("/faction")
    public ResponseEntity<List<UnitResponse>> getAllUnitsByFaction(@RequestParam String faction) {
        return ResponseEntity.ok(unitService.getAllByFaction(faction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitResponse> getUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnitById(@PathVariable Long id) {
        unitService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}