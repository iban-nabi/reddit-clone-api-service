package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.auth.Jwt;
import com.parallelquantumcorp.redditcloneapiservice.config.JwtConfiguration;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
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
