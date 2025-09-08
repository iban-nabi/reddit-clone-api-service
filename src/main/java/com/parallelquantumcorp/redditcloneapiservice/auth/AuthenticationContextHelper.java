package com.parallelquantumcorp.redditcloneapiservice.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationContextHelper {
    /**
     * Retrieves the username from the current authentication context.
     * 
     * This method extracts the principal (username) from the current security context's
     * authentication object. The security context is managed by Spring Security.
     * 
     * @return The username of the currently authenticated user as a String
     * @throws RuntimeException if no authentication context is present
     */
    public String getNameFromAuthToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}
