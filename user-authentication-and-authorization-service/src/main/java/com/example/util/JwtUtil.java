package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String CLAIMS_SUBJECT = "sub";
    private final String CLAIMS_CREATED = "created";
    @Value("${auth.jwt.secret}")
    private String TOKEN_SECRET;
    @Value("${auth.jwt.expiration}")
    private long TOKEN_EXPIRATION;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = getClaims(userDetails);
        Date expirationDate = getExpirationDate();

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Map<String, Object> getClaims(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_SUBJECT, userDetails.getUsername());
        claims.put(CLAIMS_CREATED, new Date());
        return claims;
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + TOKEN_EXPIRATION * 1000);
    }

    public String getUserName(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
    }
}