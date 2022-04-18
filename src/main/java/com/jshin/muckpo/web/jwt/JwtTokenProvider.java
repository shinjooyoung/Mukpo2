package com.jshin.muckpo.web.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Date;


/**
 * JWT 토큰 생성, 검증 클래스
 */
public class JwtTokenProvider {
    // 30분
    private long validationSecond = 60 * 30;

    private final String SECRET_KEY = "secretKey-authorization-jwt-manage-token";

    public JwtTokenProvider() {
    }
    public JwtTokenProvider(Long validationSecond) {
        this.validationSecond = validationSecond;
    }

    /**
     * SecreteKey 객체 변환
     * @param secretKey
     * @return
     */
    private Key getSigninKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 토큰 만료 확인
     * @param token
     * @return
     */
    public boolean validToken(String token) {
        Date expiration = getBody(token).getExpiration();
        return expiration.after(new Date());
    }

    /**
     * 페이로드 정보
     * @param token
     * @return
     */
    private Claims getBody(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigninKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new InvalidParameterException("유효하지 않은 토큰입니다");
        }

    }

    /**
     * 유저 ID 조회
     * @param token 
     * @return
     */
    public String getUserId(String token) {
        return getBody(token).get("userId", String.class);
    }

    /**
     * Access Token 생성
     * @param userId 
     * @return
     */
    public String createAccessToken(String userId) {
        return generateToken(userId, validationSecond * 1000l);
    }


    /**
     * Token 생성
     * @param userId 
     * @param expireTime
     * @return
     */
    private String generateToken(String userId, Long expireTime) {
        Claims claims = Jwts.claims();
//        claims.setSubject("JwtToken");
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigninKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }


}
