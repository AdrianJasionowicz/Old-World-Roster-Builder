package com.armybuilderv2.armyBuilderV2.unitStats;

import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    private StatsCalculatorService statsCalculatorService;


    public StatsController(StatsCalculatorService statsCalculatorService) {
        this.statsCalculatorService = statsCalculatorService;
    }

    @GetMapping("/army-unit/{id}/details")
    public ResponseEntity<UnitDetails> getUnitDetails(@PathVariable Long id) {
       return ResponseEntity.ok(statsCalculatorService.getUnitDetails(id));
    }
}
