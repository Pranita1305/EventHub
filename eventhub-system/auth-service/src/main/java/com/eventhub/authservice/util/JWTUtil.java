package com.eventhub.authservice.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JWTUtil {

    private final String SECRET_KEY="abc";

    public String generateToken(String email,String password,String role){
        return Jwts.builder()
                .claim("userId",userId)
                .claim("email", email)
                .claim("role", role)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims extractClaims(String token) throws ExpiredJwtException{
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String getRoleFromToken(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public Long getUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }
}