package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    @Value("${app.secret}")
    private String secret;

    public String generateToken(String subject){
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("spring-boot-app")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60)))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpiryDate(String token){
        return getClaims(token).getExpiration();
    }

    public String getUserName(String token){
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        Date expiryDate = getExpiryDate(token);
        return expiryDate.before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token, String username){
        String tokenUserName = getUserName(token);
        return (username.equals(tokenUserName) && !isTokenExpired(token));
    }
}
