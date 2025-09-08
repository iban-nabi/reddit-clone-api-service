package com.parallelquantumcorp.redditcloneapiservice.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.*;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Represents a JSON Web Token (JWT) wrapper class that handles JWT operations.
 * This class provides functionality to manage JWT claims and secret keys, along with
 * basic JWT operations like getting subject, string representation, and expiration check.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Jwt {
    private Claims claims;
    private SecretKey secretKey;

    public String getSubject(){
        return claims.getSubject();
    }

    public String toString(){
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

    public boolean isExpired(){
        try {
            return claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

}
