package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NewArmiesIntegrationTest extends IntegrationTest {

    @ParameterizedTest
    @CsvSource({
            "EMPIRE, 29", "DWARFS, 23", "ORCS_AND_GOBLINS, 27",
            "VAMPIRE_COUNTS, 24", "HIGH_ELVES, 21", "DARK_ELVES, 22", "LIZARDMEN, 20", "BRETONIA, 14",
            "CHAOS_WARRIORS, 22", "TOMB_KINGS, 23", "WOOD_ELVES, 18", "CHAOS_DWARFS, 16",
            "BEASTMEN, 22", "OGRE_KINGDOMS, 20", "DAEMONS_OF_CHAOS, 28"
    })
    @DisplayName("Every new army has its units in the catalog")
    void catalogIsLoaded(String faction, int units) throws Exception {
        var user = registerAndLogin();

        mockMvc.perform(get("/units").param("faction", faction).cookie(user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(units));
    }

    @ParameterizedTest
    @CsvSource(nullValues = "-", value = {
            "CHAOS_WARRIORS, Chaos Warriors, 4, -",
            "LIZARDMEN, Saurus Warriors, 4, -",
            "BRETONIA, Knights of the Realm, 2, 5",
            "HIGH_ELVES, Phoenix Guard, 5, 4",
            "VAMPIRE_COUNTS, Skeleton Warriors, 5, -",
            "BEASTMEN, Bestigors, 5, -",
            "OGRE_KINGDOMS, Ironguts, 5, -",
            "DAEMONS_OF_CHAOS, Bloodletters, -, 5"
    })
    @DisplayName("Free equipment of new army units adds up to the right saves")
    void savesOfNewArmies(String faction, String unitName, Integer armourSave, Integer wardSave) throws Exception {
        var user = registerAndLogin();
        var armyId = createArmy(user, faction);

        var armyUnit = addUnit(user, armyId, faction, unitName);
        mockMvc.perform(get("/army-units/{id}/stats", armyUnit).cookie(user))
                .andExpect(jsonPath("$.basicSave").value(armourSave))
                .andExpect(jsonPath("$.wardSave").value(wardSave));
    }

    @Test
    @DisplayName("Empire Swordsmen get light armour and shield, a Captain cannot take a Runefang")
    void empireUnitsWork() throws Exception {
        var user = registerAndLogin();
        var armyId = createArmy(user, "EMPIRE");

        var swordsmen = addUnit(user, armyId, "EMPIRE", "Swordsmen");
        mockMvc.perform(get("/army-units/{id}/stats", swordsmen).cookie(user))
                .andExpect(jsonPath("$.basicSave").value(5))
                .andExpect(jsonPath("$.ws").value(4));

        var captain = addUnit(user, armyId, "EMPIRE", "Captain of the Empire");
        mockMvc.perform(get("/army-units/{id}/upgrades", captain).cookie(user))
                .andExpect(jsonPath("$[?(@.name == 'Runefang')].allowed").value(false))
                .andExpect(jsonPath("$[?(@.name == 'Sword of Might')].allowed").value(true))
                .andExpect(jsonPath("$.length()").value(greaterThan(40)));
    }

    @Test
    @DisplayName("Dwarf Ironbreakers have gromril armour and a shield")
    void dwarfUnitsWork() throws Exception {
        var user = registerAndLogin();
        var armyId = createArmy(user, "DWARFS");

        var ironbreakers = addUnit(user, armyId, "DWARFS", "Ironbreakers");
        mockMvc.perform(get("/army-units/{id}/stats", ironbreakers).cookie(user))
                .andExpect(jsonPath("$.basicSave").value(3));
    }

    @Test
    @DisplayName("Orc Boyz cost 6 points per model")
    void orcUnitsWork() throws Exception {
        var user = registerAndLogin();
        var armyId = createArmy(user, "ORCS_AND_GOBLINS");

        addUnit(user, armyId, "ORCS_AND_GOBLINS", "Orc Boyz");
        mockMvc.perform(get("/armies/{id}/points", armyId).cookie(user))
                .andExpect(jsonPath("$.corePointsView.usedCore").value(60.0));
    }
}
