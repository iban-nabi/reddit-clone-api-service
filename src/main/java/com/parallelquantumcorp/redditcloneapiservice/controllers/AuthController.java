package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.auth.Jwt;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UserRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.service.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest userRequest){
        try{
            Authentication auth  = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequest.getUsername(),
                            userRequest.getPassword()
                    )
            );

            User user = userRepository.findByUsername(auth.getName());
            Jwt token = jwtService.generateAccessToken(user);
            return ResponseEntity.ok(token.toString());

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
