package com.example.sales.security;

import com.example.sales.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private final String jwtSecret = "S3cr3tK3yS3cr3tK3yS3cr3tK3yS3cr3tK3y"; // 256-bit
    private final long jwtExpirationMs = 86400000; // 1 jour

    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String generateJwt(String username, Set<Role> roles) {
        String rolesString = roles.stream()
                .map(role -> role.getName().name()) // RoleName enum en string
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", rolesString)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }


    public String getUsernameFromJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
