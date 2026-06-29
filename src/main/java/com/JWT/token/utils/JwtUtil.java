package com.JWT.token.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // 32 characters secret key
    private final String SECRET_KEY = "mysecretkeymysecretkeymysecretk"; // 32 characters
    private final long EXPIRATION_TIME = 1000 * 60 * 10; // 10 Minute


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // generate token
    public String generateToken(String uuid, String username, String email){
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", uuid);
        claims.put("email", email);
        long now = System.currentTimeMillis();
        return Jwts.builder().
                claims(claims).
                subject(username).
                issuedAt(new java.util.Date(now)).
                setExpiration(new java.util.Date(now + EXPIRATION_TIME)).
                signWith(getSigningKey()).
                compact();
    }

    //    extract token
    public Claims extractToken(String token){
        return Jwts.parser().
                verifyWith(getSigningKey()).
                build().
                parseSignedClaims(token).
                getPayload();
    }

}
