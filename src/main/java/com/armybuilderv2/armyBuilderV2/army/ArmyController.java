package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/armies")
public class ArmyController {

    private final ArmyService armyService;


    public ArmyController(ArmyService armyService) {
        this.armyService = armyService;
    }

    @PostMapping()
    public ResponseEntity<?> createArmy(@RequestBody CreateArmyRequest request) {
        return ResponseEntity.ok(armyService.createArmy(request));
    }


    @GetMapping
    public ResponseEntity<?> getAllArmies() {

        return ResponseEntity.ok(armyService.getAllArmiesByUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteArmyById(@PathVariable Long id) {
        armyService.deleteArmy(id);
      return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArmyById(@PathVariable Long id) {
        return ResponseEntity.ok(armyService.getArmyById(id));
    }
}
