package com.jie.redditcloneapiservice.controllers;

import com.jie.redditcloneapiservice.auth.Jwt;
import com.jie.redditcloneapiservice.config.JwtConfiguration;
import com.jie.redditcloneapiservice.dtos.request.UserRequest;
import com.jie.redditcloneapiservice.dtos.response.JwtResponse;
import com.jie.redditcloneapiservice.dummy_repositories.UserRepository;
import com.jie.redditcloneapiservice.entities.User;
import com.jie.redditcloneapiservice.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller responsible for handling authentication-related endpoints.
 * This controller manages user authentication and JWT token generation.
 *
 * @RestController Indicates that this class serves REST endpoints
 * @RequestMapping("/api/auth") Base path for all authentication endpoints
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest userRequest, HttpServletResponse response) {
        try{
            Authentication auth  = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequest.getUsername(),
                            userRequest.getPassword()
                    )
            );

            User user = userRepository.findByUsername(auth.getName());
            Jwt accessToken = jwtService.generateAccessToken(user);
            Jwt refreshToken = jwtService.generateRefreshToken(user);

            // create a cookie for the generated refresh token for security purposes
            Cookie cookie = new Cookie("refreshToken", refreshToken.toString());
            cookie.setHttpOnly(true);
            cookie.setPath("/auth/refresh-token");
            cookie.setMaxAge((int) jwtConfiguration.getRefreshTokenExpiration()); // 7 days
            cookie.setSecure(true);
            response.addCookie(cookie);

            return ResponseEntity.ok(accessToken.toString());

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshAccessToken(
            @CookieValue(value = "refreshToken") String refreshToken) {

        Jwt jwt = jwtService.parseStringToken(refreshToken);
        if(jwt==null || jwt.isExpired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwt.getSubject();
        User user = userRepository.findByUsername(username);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String accessToken = jwtService.generateRefreshToken(user).toString();
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }
}
