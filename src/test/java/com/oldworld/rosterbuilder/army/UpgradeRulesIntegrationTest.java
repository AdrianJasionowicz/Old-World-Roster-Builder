package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.IntegrationTest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpgradeRulesIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("A character can ride only one mount")
    void oneMount() throws Exception {
        var user = registerAndLogin();
        var chaosLord = addUnit(user, createArmy(user, "CHAOS_WARRIORS"), "CHAOS_WARRIORS", "Chaos Lord");

        select(user, chaosLord, "Manticore");

        mockMvc.perform(get("/army-units/{id}/upgrades", chaosLord).cookie(user))
                .andExpect(jsonPath("$[?(@.name == 'Juggernaut of Khorne')].notAllowedReason").value("Unit can take only 1 mount"));
        mockMvc.perform(post("/army-units/{id}/upgrades/{upgrade}", chaosLord, upgradeId(user, chaosLord, "Juggernaut of Khorne")).cookie(user))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("A magic item can be taken only once per army")
    void uniqueMagicItem() throws Exception {
        var user = registerAndLogin();
        var armyId = createArmy(user, "EMPIRE");
        var firstCaptain = addUnit(user, armyId, "EMPIRE", "Captain of the Empire");
        var secondCaptain = addUnit(user, armyId, "EMPIRE", "Captain of the Empire");

        select(user, firstCaptain, "Sword of Might");

        mockMvc.perform(get("/army-units/{id}/upgrades", secondCaptain).cookie(user))
                .andExpect(jsonPath("$[?(@.name == 'Sword of Might')].notAllowedReason").value("Sword of Might is already taken by another unit"));
    }

    @Test
    @DisplayName("Dwarf Lord has 125 points for runes, at most 3 runes on a weapon")
    void dwarfRunes() throws Exception {
        var user = registerAndLogin();
        var dwarfLord = addUnit(user, createArmy(user, "DWARFS"), "DWARFS", "Dwarf Lord");

        select(user, dwarfLord, "Master Rune of Skalf Blackhammer");
        select(user, dwarfLord, "Rune of Fury");
        select(user, dwarfLord, "Rune of Cleaving");
        select(user, dwarfLord, "Rune of Stone");

        mockMvc.perform(get("/army-units/{id}/upgrades", dwarfLord).cookie(user))
                .andExpect(jsonPath("$[?(@.name == 'Rune of Striking')].notAllowedReason").value("A runic magic weapon can carry only 3 runes"))
                .andExpect(jsonPath("$[?(@.name == 'Master Rune of Swiftness')].allowed").value(false))
                .andExpect(jsonPath("$[?(@.name == 'Rune of Fury')].rune").value("RUNE"));
        mockMvc.perform(get("/army-units/{id}/stats", dwarfLord).cookie(user))
                .andExpect(jsonPath("$.a").value(5))
                .andExpect(jsonPath("$.s").value(5));
    }

    @Test
    @DisplayName("The same Special unit can be taken at most 3 times")
    void duplicateSpecialUnits() throws Exception {
        var user = registerAndLogin();
        var armyId = createArmy(user, "CHAOS_WARRIORS");
        for (var i = 0; i < 4; i++) {
            addUnit(user, armyId, "CHAOS_WARRIORS", "Chosen");
        }

        mockMvc.perform(get("/armies/{id}/points", armyId).cookie(user))
                .andExpect(jsonPath("$.violations").value(hasItem("Chosen: SPECIAL units can be taken at most 3 times")))
                .andExpect(jsonPath("$.valid").value(false));
    }

    @Test
    @DisplayName("Empire regiments take detachments of half their size that go away with the regiment")
    void empireDetachments() throws Exception {
        var user = registerAndLogin();
        var armyId = createArmy(user, "EMPIRE");
        var swordsmen = addUnit(user, armyId, "EMPIRE", "Swordsmen");

        var army = mockMvc.perform(post("/army-units/{id}/detachments/{unit}", swordsmen, catalogUnitId(user, "EMPIRE", "Handgunners")).cookie(user))
                .andExpect(status().isOk())
                .andReturn();
        List<Map<String, Object>> units = read(army, "$.armyUnitResponseList[?(@.parentId == %d)]".formatted(swordsmen));
        var detachment = ((Number) units.getFirst().get("id")).longValue();
        mockMvc.perform(get("/armies/{id}", armyId).cookie(user))
                .andExpect(jsonPath("$.armyUnitResponseList[?(@.id == %d)].quantity".formatted(detachment)).value(5))
                .andExpect(jsonPath("$.armyUnitResponseList[?(@.id == %d)].canHaveDetachments".formatted(swordsmen)).value(true));

        mockMvc.perform(post("/army-units/{id}/increase", detachment).cookie(user))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("A detachment can have at most half as many models as its regiment"));
        mockMvc.perform(post("/army-units/{id}/detachments/{unit}", detachment, catalogUnitId(user, "EMPIRE", "Archers")).cookie(user))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post("/armies/{id}/units/{unit}", armyId, catalogUnitId(user, "EMPIRE", "Knights of the Empire")).cookie(user))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/army-units/{id}", swordsmen).cookie(user))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/armies/{id}", armyId).cookie(user))
                .andExpect(jsonPath("$.armyUnitResponseList.length()").value(1));
    }

    @Test
    @DisplayName("An army with detachments can be deleted")
    void deleteArmyWithDetachments() throws Exception {
        var user = registerAndLogin();
        var armyId = createArmy(user, "EMPIRE");
        var halberdiers = addUnit(user, armyId, "EMPIRE", "Halberdiers");
        mockMvc.perform(post("/army-units/{id}/detachments/{unit}", halberdiers, catalogUnitId(user, "EMPIRE", "Crossbowmen")).cookie(user))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/armies/{id}", armyId).cookie(user))
                .andExpect(status().isNoContent());
    }

    private void select(Cookie user, long armyUnitId, String upgradeName) throws Exception {
        mockMvc.perform(post("/army-units/{id}/upgrades/{upgrade}", armyUnitId, upgradeId(user, armyUnitId, upgradeName)).cookie(user))
                .andExpect(status().isNoContent());
    }
}
