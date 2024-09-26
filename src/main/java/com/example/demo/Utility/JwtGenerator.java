package com.example.demo.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JwtGenerator {

    private static final String SECRET_KEY = "mysecret123mysecret123mysecret12345";

    public static String generateJwt(String username, String email, int id) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long sevenDaysInMillis = 7 * 24 * 60 * 60 * 1000; // 7 days by millis
        Date exp = new Date(nowMillis + sevenDaysInMillis); // expires in 7 days

        String jwt = Jwts.builder()
                .setIssuer("manokompanija.eu")
                .setSubject("manokompanija.eu")
                .claim("Username", username)
                .claim("Email", email)
                .claim("UserId", id)
                .claim("DateOfLogin", new java.text.SimpleDateFormat("yyyy-MM-dd").format(now))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

}
