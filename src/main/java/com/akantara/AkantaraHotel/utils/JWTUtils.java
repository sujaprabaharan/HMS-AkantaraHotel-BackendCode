package com.akantara.AkantaraHotel.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;


// Utility service for generating, validating, and extracting information from JWT tokens
@Service
public class JWTUtils {

    // Token expiration time set to 7 days (in milliseconds)
    private static final long EXPIRATION_TIME = 1000*60*24*7;

    private final SecretKey Key;

    // Initializes the secret key used to sign and verify JWT tokens
    public JWTUtils(){

        String secreteString = "2f5a3130509748c453949e0a07fe298e7e86c57aec68bf138c26ba7547422fec";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    // Generates a new JWT token containing the username and expiration date
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    // Extracts the username from the JWT token
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Generic method to extract specific claims (e.g., subject or expiration) from the JWT token
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }

    // Validates the JWT token by comparing the username and checking if it has expired
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Checks if the JWT token is expired
    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}

