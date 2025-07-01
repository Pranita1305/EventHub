package com.eventhub.eventservice.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "abc";

    public Claims extractClaims(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String getRoleFromToken(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public Long getUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }

    public String getEmailFromToken(String token) {
        return extractClaims(token).get("email", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
