package com.anexang.app.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    /// GENERATE TOKEN ACCESSS

    public String generateAccessToken(String name) {
        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignaturekey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // validar el token de acceso
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignaturekey()).build().parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            System.out.println("Erroroorr token");
            return false;
        }
    }

    // Obtener el username del token
    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // Obtener un solo claims
    public <T> T getClaim(String token, Function<Claims, T> claimsFunction) {
        Claims claims = extrAllClaims(token);
        return claimsFunction.apply(claims);
    }

    // obtener todos los claims del token
    public Claims extrAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignaturekey()).build().parseClaimsJws(token).getBody();
    }

    // Obtener firma del token
    private Key getSignaturekey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
