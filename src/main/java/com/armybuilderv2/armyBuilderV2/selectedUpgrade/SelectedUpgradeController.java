package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import com.armybuilderv2.armyBuilderV2.selectedUpgrade.model.UpgradeViewCombined;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SelectedUpgradeController {
    private final SelectedUpgradeService selectedUpgradeService;

    public SelectedUpgradeController(SelectedUpgradeService selectedUpgradeService) {
        this.selectedUpgradeService = selectedUpgradeService;
    }

    @PostMapping("/armyUnit/{armyUnitId}/upgrades/{upgradeId}")
    public ResponseEntity<Void> selectUpgrade(@PathVariable Long armyUnitId, @PathVariable Long upgradeId) {
        selectedUpgradeService.selectUpgrade(armyUnitId, upgradeId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/armyUnit/{armyUnitId}/upgrades/{upgradeId}")
    public ResponseEntity<Void> removeUpgrade(@PathVariable Long armyUnitId,@PathVariable Long upgradeId) {
        selectedUpgradeService.removeUpgrade(armyUnitId,upgradeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/armyUnit/upgrades/{armyUnitId}")
    public ResponseEntity<List<UpgradeViewCombined>> getAllSelectedUpgrades(@PathVariable Long armyUnitId) {
        return ResponseEntity.ok(selectedUpgradeService.getUpgradeView(armyUnitId));
    }
}