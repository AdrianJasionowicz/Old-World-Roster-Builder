package com.oldworld.rosterbuilder.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private static final String SECRET = "test-secret-that-is-at-least-32-characters-long";

    private final JwtUtil jwtUtil = new JwtUtil(SECRET, false);

    @Test
    @DisplayName("generateToken creates a token that extractUsername reads back")
    void generateToken() {
        var token = jwtUtil.generateToken("skaven");

        assertEquals(3, token.split("\\.").length);
        assertEquals(Optional.of("skaven"), jwtUtil.extractUsername(token));
    }

    @Test
    @DisplayName("generateToken gives different users different tokens")
    void generateTokenPerUser() {
        assertNotEquals(jwtUtil.generateToken("skaven"), jwtUtil.generateToken("dwarf"));
    }

    @Test
    @DisplayName("JwtUtil rejects a secret shorter than 256 bits")
    void tooShortSecret() {
        assertThrows(RuntimeException.class, () -> new JwtUtil("short", false));
    }

    @Test
    @DisplayName("extractUsername of a token signed with another key is empty")
    void extractUsernameOtherKey() {
        var otherToken = new JwtUtil("another-secret-that-is-at-least-32-characters", false).generateToken("skaven");

        assertTrue(jwtUtil.extractUsername(otherToken).isEmpty());
    }

    @Test
    @DisplayName("extractUsername of a changed token is empty")
    void extractUsernameTampered() {
        var token = jwtUtil.generateToken("skaven");
        var parts = token.split("\\.");
        var tampered = parts[0] + "." + jwtUtil.generateToken("admin").split("\\.")[1] + "." + parts[2];

        assertTrue(jwtUtil.extractUsername(tampered).isEmpty());
    }

    @Test
    @DisplayName("extractUsername of garbage, an empty or a null token is empty")
    void extractUsernameGarbage() {
        assertTrue(jwtUtil.extractUsername("not-a-jwt").isEmpty());
        assertTrue(jwtUtil.extractUsername("").isEmpty());
        assertTrue(jwtUtil.extractUsername(null).isEmpty());
    }

    @Test
    @DisplayName("createAuthCookie is an httpOnly cookie valid for one day with the user's token")
    void createAuthCookie() {
        var cookie = jwtUtil.createAuthCookie("skaven");

        assertEquals(JwtUtil.COOKIE_NAME, cookie.getName());
        assertTrue(cookie.isHttpOnly());
        assertFalse(cookie.isSecure());
        assertEquals("Lax", cookie.getSameSite());
        assertEquals("/", cookie.getPath());
        assertEquals(Duration.ofDays(1), cookie.getMaxAge());
        assertEquals(Optional.of("skaven"), jwtUtil.extractUsername(cookie.getValue()));
    }

    @Test
    @DisplayName("createAuthCookie is secure when secure cookies are turned on")
    void createAuthCookieSecure() {
        assertTrue(new JwtUtil(SECRET, true).createAuthCookie("skaven").isSecure());
    }

    @Test
    @DisplayName("createAuthCookie for a blank username has no username in the token")
    void createAuthCookieBlankUsername() {
        var cookie = jwtUtil.createAuthCookie("");

        assertTrue(jwtUtil.extractUsername(cookie.getValue()).isEmpty());
    }

    @Test
    @DisplayName("clearAuthCookie is an empty cookie that expires at once")
    void clearAuthCookie() {
        var cookie = jwtUtil.clearAuthCookie();

        assertEquals(JwtUtil.COOKIE_NAME, cookie.getName());
        assertEquals("", cookie.getValue());
        assertEquals(Duration.ZERO, cookie.getMaxAge());
        assertTrue(cookie.isHttpOnly());
    }

    @Test
    @DisplayName("clearAuthCookie keeps the path of the auth cookie so the browser replaces it")
    void clearAuthCookiePath() {
        assertEquals(jwtUtil.createAuthCookie("skaven").getPath(), jwtUtil.clearAuthCookie().getPath());
    }

    @Test
    @DisplayName("clearAuthCookie is secure when secure cookies are turned on")
    void clearAuthCookieSecure() {
        assertTrue(new JwtUtil(SECRET, true).clearAuthCookie().isSecure());
    }
}
