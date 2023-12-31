package com.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtTest {

    public static void main(String[] args) {

        // generating token
        String token = Jwts.builder()
                .setId("id")
                .setSubject("subject")
                .setIssuer("issuer")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode("mysecretkey".getBytes()))
                .compact();
        System.out.println(token);

        // reading /parsing /claiming token
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encode("mysecretkey".getBytes()))
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getIssuer());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getExpiration());

    }
}
