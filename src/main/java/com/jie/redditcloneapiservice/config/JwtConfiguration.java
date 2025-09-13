package com.jie.redditcloneapiservice.config;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

/**
 * Configuration class for JWT (JSON Web Token) settings.
 * This class loads properties from application properties/yaml file with the prefix "spring.jwt".
 *
 * @property secret The secret key used for signing JWT tokens
 * @property accessTokenExpiration The expiration time in milliseconds for access tokens
 */
@Configuration
@ConfigurationProperties(prefix = "spring.jwt")
@Getter
@Setter
public class JwtConfiguration {
    private String secret;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
