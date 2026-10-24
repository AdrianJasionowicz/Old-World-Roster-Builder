package com.oldworld.rosterbuilder.unitStats;

import com.oldworld.rosterbuilder.unitStats.model.UnitDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class StatsController {

    private final StatsCalculatorService statsCalculatorService;

    @GetMapping("/army-units/{armyUnitId}/stats")
    ResponseEntity<UnitDetails> getUnitDetails(@PathVariable Long armyUnitId) {
        return ResponseEntity.ok(statsCalculatorService.getUnitDetails(armyUnitId));
    }
}
