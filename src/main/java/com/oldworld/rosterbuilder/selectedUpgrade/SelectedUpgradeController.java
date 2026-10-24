package com.oldworld.rosterbuilder.selectedUpgrade;

import com.oldworld.rosterbuilder.selectedUpgrade.model.UpgradeViewCombined;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/army-units/{armyUnitId}/upgrades")
@RequiredArgsConstructor
class SelectedUpgradeController {

    private final SelectedUpgradeService selectedUpgradeService;

    @GetMapping
    ResponseEntity<List<UpgradeViewCombined>> getUpgrades(@PathVariable Long armyUnitId) {
        return ResponseEntity.ok(selectedUpgradeService.getUpgradeView(armyUnitId));
    }

    @PostMapping("/{unitUpgradeId}")
    ResponseEntity<Void> selectUpgrade(@PathVariable Long armyUnitId, @PathVariable Long unitUpgradeId) {
        selectedUpgradeService.selectUpgrade(armyUnitId, unitUpgradeId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{unitUpgradeId}")
    ResponseEntity<Void> removeUpgrade(@PathVariable Long armyUnitId, @PathVariable Long unitUpgradeId) {
        selectedUpgradeService.removeUpgrade(armyUnitId, unitUpgradeId);
        return ResponseEntity.noContent().build();
    }
}
