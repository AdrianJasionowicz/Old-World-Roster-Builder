package com.oldworld.rosterbuilder.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class JwtUtil {

    public static final String COOKIE_NAME = "jwt";
    private static final Duration TOKEN_VALIDITY = Duration.ofDays(1);

    private final SecretKey secretKey;
    private final boolean secureCookie;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.cookie-secure:false}") boolean secureCookie) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.secureCookie = secureCookie;
    }

    public String generateToken(String username) {
        var now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(TOKEN_VALIDITY)))
                .signWith(secretKey)
                .compact();
    }

    public Optional<String> extractUsername(String token) {
        try {
            return Optional.ofNullable(Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Invalid JWT: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public ResponseCookie createAuthCookie(String username) {
        return authCookie(generateToken(username), TOKEN_VALIDITY);
    }

    public ResponseCookie clearAuthCookie() {
        return authCookie("", Duration.ZERO);
    }

    private ResponseCookie authCookie(String value, Duration maxAge) {
        return ResponseCookie.from(COOKIE_NAME, value)
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite("Lax")
                .path("/")
                .maxAge(maxAge)
                .build();
    }
}
