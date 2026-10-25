package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.IntegrationTest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountTokenIntegrationTest extends IntegrationTest {

    private static String credentials(String username) {
        return """
                {"username": "%s", "password": "secret", "email": "%s@test.com"}
                """.formatted(username, username);
    }

    private void register(String username) throws Exception {
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(credentials(username)))
                .andExpect(status().isCreated());
    }

    private Cookie login(String username) throws Exception {
        return jwtCookie(mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(credentials(username)))
                .andExpect(status().isNoContent())
                .andReturn());
    }

    private static String newUsername() {
        return "user-" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Test
    @DisplayName("After a username change the old token stops working and the new one works")
    void usernameChange() throws Exception {
        var username = newUsername();
        register(username);
        var oldToken = login(username);

        var newToken = jwtCookie(mockMvc.perform(patch("/account/username").cookie(oldToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"newUsername": "%s", "password": "secret"}
                                """.formatted(username + "-new")))
                .andExpect(status().isNoContent())
                .andReturn());

        mockMvc.perform(get("/me").cookie(oldToken)).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/me").cookie(newToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username + "-new"));
    }

    @Test
    @DisplayName("An old token does not log in a new user who takes the freed username")
    void reusedUsername() throws Exception {
        var username = newUsername();
        register(username);
        var oldToken = login(username);
        mockMvc.perform(delete("/account").cookie(oldToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"password": "secret"}
                                """))
                .andExpect(status().isNoContent());

        register(username);

        mockMvc.perform(get("/me").cookie(oldToken)).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/me").cookie(login(username))).andExpect(status().isOk());
    }

    @Test
    @DisplayName("A password change and a logout revoke the old tokens")
    void passwordChangeAndLogout() throws Exception {
        var username = newUsername();
        register(username);
        var firstToken = login(username);
        var secondToken = login(username);

        var newToken = jwtCookie(mockMvc.perform(patch("/account/password").cookie(firstToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"password": "secret", "newPassword": "secret"}
                                """))
                .andExpect(status().isNoContent())
                .andReturn());

        mockMvc.perform(get("/me").cookie(secondToken)).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/me").cookie(newToken)).andExpect(status().isOk());

        mockMvc.perform(post("/logout").cookie(newToken)).andExpect(status().isNoContent());
        mockMvc.perform(get("/me").cookie(newToken)).andExpect(status().isUnauthorized());
    }
}
