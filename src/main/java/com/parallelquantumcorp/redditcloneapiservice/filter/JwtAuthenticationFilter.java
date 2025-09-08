package com.parallelquantumcorp.redditcloneapiservice.filter;

import com.parallelquantumcorp.redditcloneapiservice.auth.Jwt;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.service.JwtService;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * Internal filter method that processes HTTP requests for JWT authentication.
     * This method validates the JWT token from the Authorization header and sets up authentication if valid.
     *
     * The method performs the following checks:
     * 1. Validates presence of Authorization header with Bearer token
     * 2. Verifies JWT token validity and expiration
     * 3. Confirms user has not deleted their account
     * 
     * If any validation fails, the request is passed to the next filter without authentication.
     *
     * @param request The HTTP servlet request
     * @param response The HTTP servlet response
     * @param filterChain The filter chain for passing the request to the next filter
     * @throws ServletException If there is an error in servlet processing
     * @throws IOException If there is an I/O error during processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        String token  = authHeader.replace("Bearer ", "");
        Jwt jwt = jwtService.parseStringToken(token);

        if(jwt==null || jwt.isExpired()){
            filterChain.doFilter(request,response);
            return;
        }

        String username = jwt.getSubject();
        if(userRepository.findByUsername(username).isArchived()){
            filterChain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
