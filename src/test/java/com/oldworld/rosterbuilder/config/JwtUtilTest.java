package com.oldworld.rosterbuilder.config;

import com.oldworld.rosterbuilder.config.JwtUtil.TokenClaims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private static final String SECRET = "test-secret-that-is-at-least-32-characters-long";

    private final JwtUtil jwtUtil = new JwtUtil(SECRET, false);

    @Test
    @DisplayName("generateToken creates a token that parseToken reads back with username and key")
    void generateToken() {
        var token = jwtUtil.generateToken("skaven", "key-1");

        assertEquals(3, token.split("\\.").length);
        assertEquals(Optional.of(new TokenClaims("skaven", "key-1")), jwtUtil.parseToken(token));
    }

    @Test
    @DisplayName("generateToken gives a new token for a new key")
    void generateTokenPerKey() {
        assertNotEquals(jwtUtil.generateToken("skaven", "key-1"), jwtUtil.generateToken("skaven", "key-2"));
    }

    @Test
    @DisplayName("JwtUtil rejects a secret shorter than 256 bits")
    void tooShortSecret() {
        assertThrows(RuntimeException.class, () -> new JwtUtil("short", false));
    }

    @Test
    @DisplayName("parseToken of a token signed with another key is empty")
    void parseTokenOtherKey() {
        var otherToken = new JwtUtil("another-secret-that-is-at-least-32-characters", false).generateToken("skaven", "key-1");

        assertTrue(jwtUtil.parseToken(otherToken).isEmpty());
    }

    @Test
    @DisplayName("parseToken of a changed token is empty")
    void parseTokenTampered() {
        var token = jwtUtil.generateToken("skaven", "key-1");
        var parts = token.split("\\.");
        var tampered = parts[0] + "." + jwtUtil.generateToken("admin", "key-2").split("\\.")[1] + "." + parts[2];

        assertTrue(jwtUtil.parseToken(tampered).isEmpty());
    }

    @Test
    @DisplayName("parseToken of an old token without a key is empty")
    void parseTokenWithoutKey() {
        var oldToken = Jwts.builder()
                .subject("skaven")
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();

        assertTrue(jwtUtil.parseToken(oldToken).isEmpty());
    }

    @Test
    @DisplayName("parseToken of garbage, an empty or a null token is empty")
    void parseTokenGarbage() {
        assertTrue(jwtUtil.parseToken("not-a-jwt").isEmpty());
        assertTrue(jwtUtil.parseToken("").isEmpty());
        assertTrue(jwtUtil.parseToken(null).isEmpty());
    }

    @Test
    @DisplayName("createAuthCookie is an httpOnly cookie valid for one day with the user's token")
    void createAuthCookie() {
        var cookie = jwtUtil.createAuthCookie("skaven", "key-1");

        assertEquals(JwtUtil.COOKIE_NAME, cookie.getName());
        assertTrue(cookie.isHttpOnly());
        assertFalse(cookie.isSecure());
        assertEquals("Lax", cookie.getSameSite());
        assertEquals("/", cookie.getPath());
        assertEquals(Duration.ofDays(1), cookie.getMaxAge());
        assertEquals(Optional.of(new TokenClaims("skaven", "key-1")), jwtUtil.parseToken(cookie.getValue()));
    }

    @Test
    @DisplayName("createAuthCookie is secure when secure cookies are turned on")
    void createAuthCookieSecure() {
        assertTrue(new JwtUtil(SECRET, true).createAuthCookie("skaven", "key-1").isSecure());
    }

    @Test
    @DisplayName("createAuthCookie for a blank username gives a token that is not accepted")
    void createAuthCookieBlankUsername() {
        var cookie = jwtUtil.createAuthCookie("", "key-1");

        assertTrue(jwtUtil.parseToken(cookie.getValue()).isEmpty());
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
        assertEquals(jwtUtil.createAuthCookie("skaven", "key-1").getPath(), jwtUtil.clearAuthCookie().getPath());
    }

    @Test
    @DisplayName("clearAuthCookie is secure when secure cookies are turned on")
    void clearAuthCookieSecure() {
        assertTrue(new JwtUtil(SECRET, true).clearAuthCookie().isSecure());
    }
}
