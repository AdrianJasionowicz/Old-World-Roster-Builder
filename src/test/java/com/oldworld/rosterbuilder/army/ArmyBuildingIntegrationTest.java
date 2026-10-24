package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.IntegrationTest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArmyBuildingIntegrationTest extends IntegrationTest {

    private static final long CLANRAT = 1;
    private static final long CLANRAT_SHIELD = 5;
    private static final long WARP_FIRE_THROWER = 7;
    private static final long RATLING_GUN = 8;

    @Test
    @DisplayName("Build a Clanrat unit with shields, check stats and points, then delete the army")
    void buildArmy() throws Exception {
        var user = registerAndLogin();
        var armyId = createArmy(user);
        var armyUnitId = addClanrats(user, armyId);

        mockMvc.perform(post("/army-units/{id}/upgrades/{upgrade}", armyUnitId, CLANRAT_SHIELD).cookie(user))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/army-units/{id}/stats", armyUnitId).cookie(user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.basicSave").value(5))
                .andExpect(jsonPath("$.wardSave").value(6));

        mockMvc.perform(get("/armies/{id}/points", armyId).cookie(user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.corePointsView.usedCore").value(90.0))
                .andExpect(jsonPath("$.violations").isEmpty());

        mockMvc.perform(delete("/army-units/{id}/upgrades/{upgrade}", armyUnitId, CLANRAT_SHIELD).cookie(user))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/armies/{id}/points", armyId).cookie(user))
                .andExpect(jsonPath("$.corePointsView.usedCore").value(80.0));

        mockMvc.perform(delete("/armies/{id}", armyId).cookie(user))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/armies/{id}", armyId).cookie(user))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("A rejected upgrade does not change the unit")
    void secondWeaponTeamIsRejected() throws Exception {
        var user = registerAndLogin();
        var armyId = createArmy(user);
        var armyUnitId = addClanrats(user, armyId);

        mockMvc.perform(post("/army-units/{id}/upgrades/{upgrade}", armyUnitId, WARP_FIRE_THROWER).cookie(user))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/army-units/{id}/upgrades", armyUnitId).cookie(user))
                .andExpect(jsonPath("$[?(@.id == %d)].allowed".formatted(RATLING_GUN)).value(false))
                .andExpect(jsonPath("$[?(@.id == %d)].notAllowedReason".formatted(RATLING_GUN)).value("Unit can take only 1 weapon team"));

        mockMvc.perform(post("/army-units/{id}/upgrades/{upgrade}", armyUnitId, RATLING_GUN).cookie(user))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Unit can take only 1 weapon team"));

        mockMvc.perform(get("/armies/{id}/points", armyId).cookie(user))
                .andExpect(jsonPath("$.corePointsView.usedCore").value(150.0));
    }

    @Test
    @DisplayName("Another user cannot see or change the army")
    void otherUserIsForbidden() throws Exception {
        var owner = registerAndLogin();
        var armyId = createArmy(owner);
        var intruder = registerAndLogin();

        mockMvc.perform(get("/armies/{id}", armyId).cookie(intruder))
                .andExpect(status().isForbidden());
        mockMvc.perform(delete("/armies/{id}", armyId).cookie(intruder))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Requests without login get 401 with the standard error body")
    void notLoggedIn() throws Exception {
        mockMvc.perform(get("/armies"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.path").value("/armies"));
    }

    @Test
    @DisplayName("Only factions with units in the catalog are offered")
    void factionsWithUnits() throws Exception {
        var user = registerAndLogin();

        mockMvc.perform(get("/units/factions").cookie(user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(contains("SKAVEN", "VAMPIRE_COUNTS", "HIGH_ELVES", "DARK_ELVES", "LIZARDMEN",
                        "BRETONIA", "EMPIRE", "DWARFS", "ORCS_AND_GOBLINS", "CHAOS_WARRIORS", "TOMB_KINGS", "WOOD_ELVES", "CHAOS_DWARFS",
                        "BEASTMEN", "OGRE_KINGDOMS", "DAEMONS_OF_CHAOS")));
    }

    @Test
    @DisplayName("Only admins can add units to the catalog")
    void userCannotAddUnits() throws Exception {
        var user = registerAndLogin();

        mockMvc.perform(post("/units").cookie(user).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    private long createArmy(Cookie user) throws Exception {
        var result = mockMvc.perform(post("/armies").cookie(user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "Test army", "pointsLimit": 2000, "faction": "SKAVEN"}
                                """))
                .andExpect(status().isCreated())
                .andReturn();
        return ((Number) read(result, "$.id")).longValue();
    }

    private long addClanrats(Cookie user, long armyId) throws Exception {
        var result = mockMvc.perform(post("/armies/{id}/units/{unit}", armyId, CLANRAT).cookie(user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.armyUnitResponseList[0].quantity").value(20))
                .andExpect(jsonPath("$.armyUnitResponseList[0].totalCost").value(80.0))
                .andReturn();
        return ((Number) read(result, "$.armyUnitResponseList[0].id")).longValue();
    }
}
