package ru.eosreign.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.eosreign.entity.UserInfo;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtService {

    private static final String REFRESH_TYPE = "refresh_token";
    private static final String ACCESS_TYPE = "access_token";
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Value("${token.access.expiration}")
    private String jwtAccessExpiration;

    @Value("${token.refresh.expiration}")
    private String jwtRefreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUsernameFromAuthHeader(String authHeader) {
        return extractUsername(authHeader.substring(BEARER_PREFIX.length()));
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof UserInfo customUserInfoDetails) {
            claims.put("id", customUserInfoDetails.getId());
            claims.put("role", customUserInfoDetails.getCredential().getRole());
            claims.put("type", ACCESS_TYPE);
        }

        Long expirationTime = Instant
                .now()
                .plus(Long.parseLong(jwtAccessExpiration), ChronoUnit.MINUTES)
                .toEpochMilli();

        return generateToken(claims, userDetails, expirationTime);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof UserInfo customUserInfoDetails) {
            claims.put("id", customUserInfoDetails.getId());
            claims.put("type", REFRESH_TYPE);
        }

        Long expirationTime = Instant
                .now()
                .plus(Long.parseLong(jwtRefreshExpiration), ChronoUnit.DAYS)
                .toEpochMilli();

        return generateToken(claims, userDetails, expirationTime);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isAccessToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("type", String.class).equals(ACCESS_TYPE);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims,
                                 UserDetails userDetails,
                                 Long expirationTime) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}