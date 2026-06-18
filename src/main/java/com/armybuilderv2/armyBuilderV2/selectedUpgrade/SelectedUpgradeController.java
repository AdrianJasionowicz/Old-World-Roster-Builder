package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SelectedUpgradeController {
    private final SelectedUpgradeService selectedUpgradeService;

    public SelectedUpgradeController(SelectedUpgradeService selectedUpgradeService) {
        this.selectedUpgradeService = selectedUpgradeService;
    }


    @PostMapping("/armyUnit/{armyUnitId}/upgrades/{upgradeId}")
    public ResponseEntity<?> selectUpgrade(@PathVariable Long armyUnitId, @PathVariable Long upgradeId) {
        selectedUpgradeService.selectUpgrade(armyUnitId, upgradeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/armyUnit/{armyUnitId}/upgrades/{upgradeId}")
    public ResponseEntity<?> removeUpgrade(@PathVariable Long armyUnitId,@PathVariable Long upgradeId) {
        selectedUpgradeService.removeUpgrade(armyUnitId,upgradeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/armyUnit/upgrades/{armyUnitId}")
    public ResponseEntity<?> getAllSelectedUpgrades(@PathVariable Long armyUnitId) {
        return ResponseEntity.ok(selectedUpgradeService.getUpgradeView(armyUnitId));

    }

}
