package com.codewithmosh.store.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    public String generateTokens(String email){
      return   Jwts.builder()
                .setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 86400))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
