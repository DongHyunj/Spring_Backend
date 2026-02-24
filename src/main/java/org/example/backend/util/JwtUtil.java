package org.example.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    static String key = "sdfkhgsdkglnhoiurjdfoihgh397478thgwr390289gyrfhp90823uoevbdo823uvh4tf";
    static SecretKey encodedKey = Keys.hmacShaKeyFor(key.getBytes());

    public static String createToken(Long idx, String email, String role) {
        String jwt = Jwts.builder()
                .claim("idx", idx)
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 300000)).signWith(encodedKey).compact();

        return jwt;
    }

    public static Long getUserIdx(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(encodedKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("idx", Long.class);
    }

    public static String getUserName(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(encodedKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("email", String.class);
    }

    public static String getUserRole(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(encodedKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("role", String.class);
    }

}
