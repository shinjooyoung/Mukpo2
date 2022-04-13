package com.sts.refund.web.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private String secretKey;
    private final long validityInMilliseconds;

    public JwtTokenProvider(String secretKey, long validityInMilliseconds) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    //토큰생성
    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("userId", userId);
        Date now = new Date();

        Date validity = new Date(now.getTime()
                + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //토큰에서 검증
    public boolean validateToken(String token) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (body.getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch(JwtException | IllegalArgumentException e) {
            return false;
        }

    }
    
}
