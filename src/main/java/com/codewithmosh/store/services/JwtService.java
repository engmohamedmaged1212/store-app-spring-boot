package com.codewithmosh.store.services;

import com.codewithmosh.store.config.JwtConfig;
import com.codewithmosh.store.config.SecurityConfig;
import com.codewithmosh.store.entities.Role;
import com.codewithmosh.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.util.BitSet.valueOf;
@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    public Jwt generateAccessTokens(User user){
      return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshTokens(User user){
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(User user, long tokenExpiration) {
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("name" , user.getName())
                .add("email" , user.getEmail())
                .add("role" , user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();
        String tokenValue = Jwts.builder()
                .claims(claims)
                .signWith(jwtConfig.getSecretKey())
                .compact();

        return new Jwt(claims , jwtConfig.getSecretKey() , tokenValue);
    }



    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Jwt parseToken(String token){
        try{
            var claims =getClaims(token);
            return new Jwt(claims , jwtConfig.getSecretKey() , token);
        } catch (JwtException e) {
            return null ;
        }
    }

}
