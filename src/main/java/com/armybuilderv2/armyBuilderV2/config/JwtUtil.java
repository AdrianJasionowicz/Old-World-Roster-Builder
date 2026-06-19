package com.armybuilderv2.armyBuilderV2.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final String SECRET_KEY = "yWv8MZ+yE7FCK8j99Kd8wOgEzxSlt+w5GpKdpj0NdI0=";
    // I know it shouldn't be here but it's only for learning purpose
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + 86400000))
                .signWith(secretKey)
                .compact();
    }

    public String getUserFromToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
           log.error("JWT validation error: " + e.getMessage());

        }
        return false;
    }

}
