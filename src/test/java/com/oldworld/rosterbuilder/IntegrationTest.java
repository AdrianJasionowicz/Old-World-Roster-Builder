package com.oldworld.rosterbuilder;

import com.jayway.jsonpath.JsonPath;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "jwt.secret=integration-test-secret-that-is-long-enough-for-hs256")
@AutoConfigureMockMvc
@Testcontainers(disabledWithoutDocker = true)
public abstract class IntegrationTest {

    @ServiceConnection
    static final PostgreSQLContainer POSTGRES = new PostgreSQLContainer("postgres:17-alpine");

    static {
        if (DockerClientFactory.instance().isDockerAvailable()) {
            POSTGRES.start();
        }
    }

    @Autowired
    protected MockMvc mockMvc;

    protected Cookie registerAndLogin() throws Exception {
        var username = "user-" + UUID.randomUUID().toString().substring(0, 8);
        var credentials = """
                {"username": "%s", "password": "secret", "email": "%s@test.com"}
                """.formatted(username, username);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(credentials))
                .andExpect(status().isCreated());

        var login = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(credentials))
                .andExpect(status().isNoContent())
                .andReturn();
        return jwtCookie(login);
    }

    protected long createArmy(Cookie user, String faction) throws Exception {
        var result = mockMvc.perform(post("/armies").cookie(user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "Test army", "pointsLimit": 2000, "faction": "%s"}
                                """.formatted(faction)))
                .andExpect(status().isCreated())
                .andReturn();
        return ((Number) read(result, "$.id")).longValue();
    }

    protected long addUnit(Cookie user, long armyId, String faction, String unitName) throws Exception {
        var army = mockMvc.perform(post("/armies/{id}/units/{unit}", armyId, catalogUnitId(user, faction, unitName)).cookie(user))
                .andExpect(status().isOk())
                .andReturn();
        List<Map<String, Object>> armyUnits = read(army, "$.armyUnitResponseList");
        return armyUnits.stream()
                .filter(unit -> unitName.equals(unit.get("unitName")))
                .mapToLong(unit -> ((Number) unit.get("id")).longValue())
                .max()
                .orElseThrow();
    }

    protected long catalogUnitId(Cookie user, String faction, String unitName) throws Exception {
        var catalog = mockMvc.perform(get("/units").param("faction", faction).cookie(user)).andReturn();
        List<Map<String, Object>> units = read(catalog, "$");
        return units.stream()
                .filter(unit -> unitName.equals(unit.get("name")))
                .map(unit -> ((Number) unit.get("id")).longValue())
                .findFirst()
                .orElseThrow();
    }

    protected long upgradeId(Cookie user, long armyUnitId, String upgradeName) throws Exception {
        var upgrades = mockMvc.perform(get("/army-units/{id}/upgrades", armyUnitId).cookie(user)).andReturn();
        List<Map<String, Object>> options = read(upgrades, "$");
        return options.stream()
                .filter(option -> upgradeName.equals(option.get("name")))
                .map(option -> ((Number) option.get("id")).longValue())
                .findFirst()
                .orElseThrow();
    }

    protected static Cookie jwtCookie(MvcResult result) {
        var setCookie = result.getResponse().getHeader(HttpHeaders.SET_COOKIE);
        var value = setCookie.substring("jwt=".length(), setCookie.indexOf(';'));
        return new Cookie("jwt", value);
    }

    protected static <T> T read(MvcResult result, String jsonPath) throws Exception {
        return JsonPath.read(result.getResponse().getContentAsString(), jsonPath);
    }
}
