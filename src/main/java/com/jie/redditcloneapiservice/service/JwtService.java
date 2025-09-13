package com.jie.redditcloneapiservice.service;

import com.jie.redditcloneapiservice.auth.Jwt;
import com.jie.redditcloneapiservice.config.JwtConfiguration;
import com.jie.redditcloneapiservice.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfiguration jwtConfiguration;

    public Jwt generateAccessToken(User user){
        return generateJwt(user, jwtConfiguration.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user){
        return generateJwt(user, jwtConfiguration.getRefreshTokenExpiration());
    }

    /**
     * Generates a JSON Web Token (JWT) for the specified user.
     *
     * @param user The user for whom the JWT is being generated
     * @param expiration The token expiration time in seconds
     * @return A Jwt object containing the generated token with claims and secret key
     * @throws IllegalArgumentException if user is null or expiration is negative
     */
    public Jwt generateJwt(User user, long expiration){
        Claims claims = Jwts.claims()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * expiration))
                .build();

        return Jwt.builder()
                .claims(claims)
                .secretKey(jwtConfiguration.getSecretKey())
                .build();
    }

    /**
     * Parses a JWT token string into a Jwt object.
     * 
     * @param token The JWT token string to parse
     * @return A Jwt object containing the parsed claims and secret key, or null if parsing fails
     * @throws JwtException If the token is invalid or cannot be parsed
     */
    public Jwt parseStringToken(String token){
        try{
            Claims claims = Jwts.parser()
                    .verifyWith(jwtConfiguration.getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return Jwt.builder()
                    .claims(claims)
                    .secretKey(jwtConfiguration.getSecretKey())
                    .build();
        }catch (JwtException e){
            return null;
        }
    }
}
