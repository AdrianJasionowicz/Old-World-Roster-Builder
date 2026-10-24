package com.oldworld.rosterbuilder.unit;

import com.oldworld.rosterbuilder.unit.model.UnitRequest;
import com.oldworld.rosterbuilder.unit.model.UnitResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
class UnitController {

    private final UnitService unitService;

    @PostMapping
    ResponseEntity<UnitResponse> addUnit(@Valid @RequestBody UnitRequest unitRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(unitService.addUnit(unitRequest));
    }

    @GetMapping
    ResponseEntity<List<UnitResponse>> getAllUnitsByFaction(@RequestParam UnitFaction faction) {
        return ResponseEntity.ok(unitService.getAllByFaction(faction));
    }

    @GetMapping("/factions")
    ResponseEntity<List<UnitFaction>> getFactionsWithUnits() {
        return ResponseEntity.ok(unitService.getFactionsWithUnits());
    }

    @GetMapping("/{id}")
    ResponseEntity<UnitResponse> getUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUnitById(@PathVariable Long id) {
        unitService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
